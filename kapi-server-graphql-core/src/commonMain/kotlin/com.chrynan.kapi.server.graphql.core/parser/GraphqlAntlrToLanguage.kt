package com.chrynan.kapi.server.graphql.core.parser

import com.chrynan.kapi.server.graphql.core.antlr.GraphqlLexer
import com.chrynan.kapi.server.graphql.core.antlr.GraphqlParser
import com.chrynan.kapi.server.graphql.core.i18n.I18n
import com.chrynan.locale.core.Locale
import com.chrynan.locale.core.getDefault
import org.antlr.v4.kotlinruntime.CommonTokenStream
import org.antlr.v4.kotlinruntime.ParserRuleContext
import org.antlr.v4.kotlinruntime.Token
import org.antlr.v4.kotlinruntime.tree.TerminalNode
import kotlin.String
import com.chrynan.kapi.server.graphql.core.antlr.GraphqlParser.DocumentContext
import com.chrynan.kapi.server.graphql.core.antlr.GraphqlParser.DefinitionContext
import com.chrynan.kapi.server.graphql.core.language.*
import com.chrynan.kapi.server.graphql.core.parser.Parser.Companion.CHANNEL_COMMENTS
import com.chrynan.kapi.server.graphql.core.parser.Parser.Companion.CHANNEL_WHITESPACE
import com.chrynan.kapi.server.graphql.core.util.Assert
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kotlinx.serialization.json.*

class GraphqlAntlrToLanguage(
    private val tokens: CommonTokenStream,
    private val multiSourceReader: MultiSourceReader,
    private val parserOptions: ParserOptions = ParserOptions.defaultParserOptions,
    private val i18N: I18n = I18n(bundleType = I18n.BundleType.Parsing, locale = Locale.getDefault()),
    private val nodeToRuleMap: MutableMap<Node, ParserRuleContext?> = mutableMapOf()
) {

    fun createDocument(context: DocumentContext): Document {
        val definitions = context.findDefinition()
            .map { createDefinition(it) }
        val document = Document(
            definitions = definitions,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(document, context)
    }

    private fun createDefinition(context: DefinitionContext): Definition =
        context.findOperationDefinition()?.let { createOperationDefinition(it) }
            ?: context.findFragmentDefinition()?.let { createFragmentDefinition(it) }
            ?: context.findTypeSystemDefinition()?.let { createTypeSystemDefinition(it) }
            ?: context.findTypeSystemExtension()?.let { createTypeSystemExtension(it) }
            ?: error("Unexpected DefinitionContext value: $context")

    private fun createOperationDefinition(context: GraphqlParser.OperationDefinitionContext): OperationDefinition {
        val operationDefinition = OperationDefinition(
            operation = context.findOperationType()?.let { parseOperation(it) } ?: Operation.QUERY,
            name = context.findName()?.text ?: "",
            variables = context.findVariableDefinitions()?.let { createVariableDefinitions(it) } ?: emptyList(),
            selectionSet = context.findSelectionSet()?.let { createSelectionSet(it) } ?: SelectionSet(),
            directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList(),
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(operationDefinition, context)
    }

    private fun parseOperation(operationTypeContext: GraphqlParser.OperationTypeContext?): Operation =
        operationTypeContext?.text?.let { Operation.getBySerialName(it) } ?: Assert.assertShouldNeverHappen()

    private fun createFragmentSpread(context: GraphqlParser.FragmentSpreadContext): FragmentSpread? {
        val name = context.findFragmentName()?.text ?: return null
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val fragment = FragmentSpread(
            name = name,
            directives = directives,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(fragment, context)
    }

    private fun createVariableDefinitions(context: GraphqlParser.VariableDefinitionsContext?): List<VariableDefinition> =
        context?.findVariableDefinition()?.mapNotNull { createVariableDefinition(it) } ?: emptyList()

    private fun createVariableDefinition(context: GraphqlParser.VariableDefinitionContext): VariableDefinition? {
        val name = context.findVariable()?.findName()?.text ?: return null
        val type = context.findType()?.let { createType(it) } ?: return null
        val defaultValue = context.findDefaultValue()?.findValue()?.let { createValue(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val variable = VariableDefinition(
            name = name,
            type = type,
            defaultValue = defaultValue,
            directives = directives,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(variable, context)
    }

    private fun createFragmentDefinition(context: GraphqlParser.FragmentDefinitionContext): FragmentDefinition? {
        val name = context.findFragmentName()?.text ?: return null
        val typeCondition = context.findTypeCondition()?.findTypeName()?.let { createTypeName(it) } ?: return null
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val selectionSet = context.findSelectionSet()?.let { createSelectionSet(it) } ?: SelectionSet()
        val fragment = FragmentDefinition(
            name = name,
            typeCondition = typeCondition,
            selectionSet = selectionSet,
            directives = directives,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(fragment, context)
    }

    private fun createSelectionSet(context: GraphqlParser.SelectionSetContext?): SelectionSet? {
        if (context == null) {
            return null
        }

        val selections = context.findSelection().mapNotNull { createSelection(it) }
        val selectionSet = SelectionSet(
            selections = selections,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(selectionSet, context)
    }

    private fun createSelection(context: GraphqlParser.SelectionContext): Selection? =
        context.findField()?.let { createField(it) }
            ?: context.findFragmentSpread()?.let { createFragmentSpread(it) }
            ?: context.findInlineFragment()?.let { createInlineFragment(it) }
            ?: Assert.assertShouldNeverHappen()

    private fun createField(context: GraphqlParser.FieldContext): Field? {
        val name = context.findName()?.text ?: return null
        val alias = context.findAlias()?.text
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val arguments = context.findArguments()?.let { createArguments(it) } ?: emptyList()
        val selectionSet = createSelectionSet(context.findSelectionSet())
        val field = Field(
            name = name,
            alias = alias,
            arguments = Arguments(arguments = arguments),
            directives = directives,
            selectionSet = selectionSet,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(field, context)
    }

    private fun createInlineFragment(context: GraphqlParser.InlineFragmentContext): InlineFragment {
        val typeCondition = context.findTypeCondition()?.findTypeName()?.let { createTypeName(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val selectionSet = context.findSelectionSet()?.let { createSelectionSet(it) } ?: SelectionSet()
        val inlineFragment = InlineFragment(
            typeCondition = typeCondition,
            directives = directives,
            selectionSet = selectionSet,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(inlineFragment, context)
    }

    //MARKER END: Here GraphqlOperation.g4 specific methods end
    private fun createTypeSystemDefinition(context: GraphqlParser.TypeSystemDefinitionContext): SDLDefinition =
        context.findSchemaDefinition()?.let { createSchemaDefinition(it) }
            ?: context.findTypeDefinition()?.let { createTypeDefinition(it) }
            ?: context.findDirectiveDefinition()?.let { createDirectiveDefinition(it) }
            ?: Assert.assertShouldNeverHappen()

    private fun createTypeSystemExtension(context: GraphqlParser.TypeSystemExtensionContext): SDLDefinition =
        context.findTypeExtension()?.let { createTypeExtension(it) }
            ?: context.findSchemaExtension()?.let { creationSchemaExtension(it) }
            ?: Assert.assertShouldNeverHappen()

    private fun createTypeExtension(context: GraphqlParser.TypeExtensionContext): TypeDefinition =
        context.findEnumTypeExtensionDefinition()?.let { createEnumTypeExtensionDefinition(it) }
            ?: context.findObjectTypeExtensionDefinition()?.let { createObjectTypeExtensionDefinition(it) }
            ?: context.findInputObjectTypeExtensionDefinition()?.let { createInputObjectTypeExtensionDefinition(it) }
            ?: context.findInterfaceTypeExtensionDefinition()?.let { createInterfaceTypeExtensionDefinition(it) }
            ?: context.findScalarTypeExtensionDefinition()?.let { createScalarTypeExtensionDefinition(it) }
            ?: context.findUnionTypeExtensionDefinition()?.let { createUnionTypeExtensionDefinition(it) }
            ?: Assert.assertShouldNeverHappen()

    private fun createTypeDefinition(context: GraphqlParser.TypeDefinitionContext): TypeDefinition =
        context.findEnumTypeDefinition()?.let { createEnumTypeDefinition(it) }
            ?: context.findObjectTypeDefinition()?.let { createObjectTypeDefinition(it) }
            ?: context.findInputObjectTypeDefinition()?.let { createInputObjectTypeDefinition(it) }
            ?: context.findInterfaceTypeDefinition()?.let { createInterfaceTypeDefinition(it) }
            ?: context.findScalarTypeDefinition()?.let { createScalarTypeDefinition(it) }
            ?: context.findUnionTypeDefinition()?.let { createUnionTypeDefinition(it) }
            ?: Assert.assertShouldNeverHappen()

    private fun createType(context: GraphqlParser.TypeContext): Type? =
        context.findTypeName()?.let { createTypeName(it) }
            ?: context.findNonNullType()?.let { createNonNullType(it) }
            ?: context.findListType()?.let { createListType(it) }
            ?: Assert.assertShouldNeverHappen()

    private fun createTypeName(context: GraphqlParser.TypeNameContext): TypeName? {
        val name = context.findName()?.text ?: return null
        val typeName = TypeName(
            name = name,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext<TypeName?>(typeName, context)
    }

    private fun createNonNullType(context: GraphqlParser.NonNullTypeContext): NonNullType {
        val type = context.findListType()?.let { createListType(it) }
            ?: context.findTypeName()?.let { createTypeName(it) }
            ?: Assert.assertShouldNeverHappen()

        val nonNullType = NonNullType(
            type = type,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(nonNullType, context)
    }

    private fun createListType(context: GraphqlParser.ListTypeContext): ListType {
        val type = context.findType()?.let { createType(it) }
            ?: Assert.assertShouldNeverHappen()

        val listType = ListType(
            type = type,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(listType, context)
    }

    private fun createArgument(context: GraphqlParser.ArgumentContext): Argument? {
        val name = context.findName()?.text ?: return null
        val value = context.findValueWithVariable()?.let { createValue(it) } ?: return null
        val argument = Argument(
            name = name,
            value = value,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(argument, context)
    }

    private fun createArguments(context: GraphqlParser.ArgumentsContext?): List<Argument> =
        context?.findArgument()?.mapNotNull { createArgument(it) } ?: emptyList()

    private fun createDirectives(context: GraphqlParser.DirectivesContext?): List<Directive> =
        context?.findDirective()?.mapNotNull { createDirective(it) } ?: emptyList()

    private fun createDirective(context: GraphqlParser.DirectiveContext): Directive? {
        val name = context.findName()?.text ?: return null
        val arguments = context.findArguments()?.let { createArguments(it) } ?: emptyList()
        val directive = Directive(
            name = name,
            arguments = Arguments(arguments = arguments),
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(directive, context)
    }

    private fun createSchemaDefinition(context: GraphqlParser.SchemaDefinitionContext): SchemaDefinition {
        val description = context.findDescription()?.let { createDescription(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val operationTypes = context.findOperationTypeDefinition().mapNotNull {
            createOperationTypeDefinition(it)
        }
        val schema = SchemaDefinition(
            types = operationTypes,
            directives = directives,
            description = description,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(schema, context)
    }

    private fun creationSchemaExtension(context: GraphqlParser.SchemaExtensionContext): SDLDefinition? {
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val operationTypes = context.findOperationTypeDefinition().mapNotNull {
            createOperationTypeDefinition(it)
        }
        val schema = SchemaExtensionDefinition(
            types = operationTypes,
            directives = directives,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext<SchemaExtensionDefinition?>(schema, context)
    }

    private fun createOperationTypeDefinition(context: GraphqlParser.OperationTypeDefinitionContext): OperationTypeDefinition? {
        val typeName = context.findTypeName()?.let { createTypeName(it) } ?: return null
        val name = context.findOperationType()?.text ?: return null
        val type = OperationTypeDefinition(
            name = name,
            typeName = typeName,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(type, context)
    }

    private fun createScalarTypeDefinition(context: GraphqlParser.ScalarTypeDefinitionContext): ScalarTypeDefinition? {
        val name = context.findName()?.text ?: return null
        val description = context.findDescription()?.let { createDescription(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val scalar = ScalarTypeDefinition(
            name = name,
            description = description,
            directives = directives,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(scalar, context)
    }

    private fun createScalarTypeExtensionDefinition(context: GraphqlParser.ScalarTypeExtensionDefinitionContext): ScalarTypeExtensionDefinition? {
        val name = context.findName()?.text ?: return null
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val scalar = ScalarTypeExtensionDefinition(
            name = name,
            directives = directives,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(scalar, context)
    }

    private fun createObjectTypeDefinition(context: GraphqlParser.ObjectTypeDefinitionContext): ObjectTypeDefinition? {
        val name = context.findName()?.text ?: return null
        val description = context.findDescription()?.let { createDescription(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val implements = context.findImplementsInterfaces()?.let { getImplements(it) } ?: emptyList()
        val fieldDefinitions = context.findFieldsDefinition()?.let { createFieldDefinitions(it) } ?: emptyList()
        val objectType = ObjectTypeDefinition(
            name = name,
            description = description,
            directives = directives,
            implements = implements,
            fieldDefinitions = fieldDefinitions,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(objectType, context)
    }

    private fun createObjectTypeExtensionDefinition(context: GraphqlParser.ObjectTypeExtensionDefinitionContext): ObjectTypeExtensionDefinition? {
        val name = context.findName()?.text ?: return null
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val implements = context.findImplementsInterfaces()?.let { getImplements(it) } ?: emptyList()
        val fieldDefinitions =
            context.findExtensionFieldsDefinition()?.let { createFieldDefinitions(it) } ?: emptyList()
        val objectType = ObjectTypeExtensionDefinition(
            name = name,
            directives = directives,
            implements = implements,
            fieldDefinitions = fieldDefinitions,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(objectType, context)
    }

    private fun createFieldDefinitions(context: GraphqlParser.FieldsDefinitionContext?): List<FieldDefinition> =
        context?.findFieldDefinition()?.mapNotNull { createFieldDefinition(it) } ?: emptyList()

    private fun createFieldDefinitions(context: GraphqlParser.ExtensionFieldsDefinitionContext?): List<FieldDefinition> =
        context?.findFieldDefinition()?.mapNotNull { createFieldDefinition(it) } ?: emptyList()

    private fun createFieldDefinition(context: GraphqlParser.FieldDefinitionContext): FieldDefinition? {
        val name = context.findName()?.text ?: return null
        val type = context.findType()?.let { createType(it) } ?: return null
        val description = context.findDescription()?.let { createDescription(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val values = context.findArgumentsDefinition()
            ?.findInputValueDefinition()
            ?.let { createInputValueDefinitions(it) }
            ?: emptyList()
        val field = FieldDefinition(
            name = name,
            type = type,
            values = values,
            directives = directives,
            description = description,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(field, context)
    }

    private fun createInputValueDefinitions(definitions: List<GraphqlParser.InputValueDefinitionContext>?): List<InputValueDefinition> =
        definitions?.mapNotNull { createInputValueDefinition(it) } ?: emptyList()

    private fun createInputValueDefinition(context: GraphqlParser.InputValueDefinitionContext): InputValueDefinition? {
        val name = context.findName()?.text ?: return null
        val type = context.findType()?.let { createType(it) } ?: return null
        val description = context.findDescription()?.let { createDescription(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val defaultValue = context.findDefaultValue()?.findValue()?.let { createValue(it) }
        val value = InputValueDefinition(
            name = name,
            type = type,
            description = description,
            directives = directives,
            defaultValue = defaultValue,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(value, context)
    }

    private fun createInterfaceTypeDefinition(context: GraphqlParser.InterfaceTypeDefinitionContext): InterfaceTypeDefinition? {
        val name = context.findName()?.text ?: return null
        val description = context.findDescription()?.let { createDescription(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: return null
        val implements = context.findImplementsInterfaces()?.let { getImplements(it) } ?: emptyList()
        val fieldDefinitions = context.findFieldsDefinition()?.let { createFieldDefinitions(it) } ?: emptyList()
        val interfaceType = InterfaceTypeDefinition(
            name = name,
            description = description,
            directives = directives,
            implements = implements,
            fieldDefinitions = fieldDefinitions,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(interfaceType, context)
    }

    private fun createInterfaceTypeExtensionDefinition(context: GraphqlParser.InterfaceTypeExtensionDefinitionContext): InterfaceTypeExtensionDefinition? {
        val name = context.findName()?.text ?: return null
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: return null
        val implements = context.findImplementsInterfaces()?.let { getImplements(it) } ?: emptyList()
        val fieldDefinitions =
            context.findExtensionFieldsDefinition()?.let { createFieldDefinitions(it) } ?: emptyList()
        val interfaceType = InterfaceTypeExtensionDefinition(
            name = name,
            directives = directives,
            implements = implements,
            fieldDefinitions = fieldDefinitions,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(interfaceType, context)
    }

    private fun createUnionTypeDefinition(context: GraphqlParser.UnionTypeDefinitionContext): UnionTypeDefinition? {
        val name = context.findName()?.text ?: return null
        val description = context.findDescription()?.let { createDescription(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val members = mutableListOf<TypeName>()
        context.findUnionMembership()?.let { unionMembership ->
            var unionMembersContext = unionMembership.findUnionMembers()
            while (unionMembersContext != null) {
                unionMembersContext.findUnionMembers()
                    ?.findTypeName()
                    ?.let { createTypeName(it) }
                    ?.let { members.add(0, it) }
                unionMembersContext = unionMembersContext.findUnionMembers()
            }
        }
        val union = UnionTypeDefinition(
            name = name,
            description = description,
            directives = directives,
            memberTypes = members,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(union, context)
    }

    private fun createUnionTypeExtensionDefinition(context: GraphqlParser.UnionTypeExtensionDefinitionContext): UnionTypeExtensionDefinition? {
        val name = context.findName()?.text ?: return null
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val members = mutableListOf<TypeName>()
        context.findUnionMembership()?.let { unionMembership ->
            var unionMembersContext = unionMembership.findUnionMembers()
            while (unionMembersContext != null) {
                unionMembersContext.findUnionMembers()
                    ?.findTypeName()
                    ?.let { createTypeName(it) }
                    ?.let { members.add(0, it) }
                unionMembersContext = unionMembersContext.findUnionMembers()
            }
        }
        val union = UnionTypeExtensionDefinition(
            name = name,
            directives = directives,
            memberTypes = members,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(union, context)
    }

    private fun createEnumTypeDefinition(context: GraphqlParser.EnumTypeDefinitionContext): EnumTypeDefinition? {
        val name = context.findName()?.text ?: return null
        val description = context.findDescription()?.let { createDescription(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val enumValues = context.findEnumValueDefinitions()?.findEnumValueDefinition()?.mapNotNull {
            createEnumValueDefinition(it)
        } ?: emptyList()
        val enumType = EnumTypeDefinition(
            name = name,
            values = enumValues,
            directives = directives,
            description = description,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(enumType, context)
    }

    private fun createEnumTypeExtensionDefinition(context: GraphqlParser.EnumTypeExtensionDefinitionContext): EnumTypeExtensionDefinition? {
        val name = context.findName()?.text ?: return null
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val enumValues = context.findExtensionEnumValueDefinitions()?.findEnumValueDefinition()?.mapNotNull {
            createEnumValueDefinition(it)
        } ?: emptyList()
        val enumType = EnumTypeExtensionDefinition(
            name = name,
            values = enumValues,
            directives = directives,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(enumType, context)
    }

    private fun createEnumValueDefinition(context: GraphqlParser.EnumValueDefinitionContext): EnumValueDefinition? {
        val name = context.findEnumValue()?.text ?: return null
        val description = context.findDescription()?.let { createDescription(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val definition = EnumValueDefinition(
            name = name,
            description = description,
            directives = directives,
            sourceLocation = getSourceLocation(context),
            ignoredChars = getIgnoredChars(context),
            comments = getComments(context)
        )

        return captureRuleContext(definition, context)
    }

    private fun createInputObjectTypeDefinition(context: GraphqlParser.InputObjectTypeDefinitionContext): InputObjectTypeDefinition? {
        val name = context.findName()?.text ?: return null
        val description = context.findDescription()?.let { createDescription(it) }
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val values = context.findInputObjectValueDefinitions()?.findInputValueDefinition()?.let {
            createInputValueDefinitions(it)
        } ?: emptyList()
        val inputObject = InputObjectTypeDefinition(
            name = name,
            description = description,
            directives = directives,
            values = values,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(inputObject, context)
    }

    private fun createInputObjectTypeExtensionDefinition(context: GraphqlParser.InputObjectTypeExtensionDefinitionContext): InputObjectTypeExtensionDefinition? {
        val name = context.findName()?.text ?: return null
        val directives = context.findDirectives()?.let { createDirectives(it) } ?: emptyList()
        val values = context.findExtensionInputObjectValueDefinitions()?.findInputValueDefinition()?.let {
            createInputValueDefinitions(it)
        } ?: emptyList()
        val inputObject = InputObjectTypeExtensionDefinition(
            name = name,
            directives = directives,
            values = values,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(inputObject, context)
    }

    private fun createDirectiveDefinition(context: GraphqlParser.DirectiveDefinitionContext): DirectiveDefinition? {
        val name = context.findName()?.text ?: return null
        val description = context.findDescription()?.let { createDescription(it) }
        val isRepeatable = context.REPEATABLE() != null
        val inputValues = context.findArgumentsDefinition()?.findInputValueDefinition()?.let {
            createInputValueDefinitions(it)
        } ?: emptyList()

        val locations = mutableListOf<DirectiveLocation>()
        var directiveLocationContext = context.findDirectiveLocations()
        while (directiveLocationContext != null) {
            directiveLocationContext.findDirectiveLocation()?.let {
                createDirectiveLocation(it)
            }?.let {
                locations.add(0, it)
            }
            directiveLocationContext = directiveLocationContext.findDirectiveLocations()
        }

        val directive = DirectiveDefinition(
            name = name,
            isRepeatable = isRepeatable,
            description = description,
            inputValues = inputValues,
            locations = locations,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(directive, context)
    }

    private fun createDirectiveLocation(context: GraphqlParser.DirectiveLocationContext): DirectiveLocation {
        val name = context.findName()?.text ?: ""
        val directiveLocation = DirectiveLocation(
            name = name,
            sourceLocation = getSourceLocation(context),
            comments = getComments(context),
            ignoredChars = getIgnoredChars(context)
        )

        return captureRuleContext(directiveLocation, context)
    }

    private fun createValue(context: GraphqlParser.ValueWithVariableContext): Value =
        context.BooleanValue()?.text?.toBooleanStrictOrNull()?.let {
            BooleanValue(
                value = it,
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.IntValue()?.text?.toBigInteger()?.let {
            IntValue(
                value = it,
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.FloatValue()?.text?.toBigDecimal()?.let {
            FloatValue(
                value = it,
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.StringValue()?.let { quotedString(it) }?.let {
            StringValue(
                value = it,
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.NullValue()?.let {
            NullValue(
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.findEnumValue()?.findEnumValueName()?.text?.let {
            EnumValue(
                name = it,
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.findArrayValueWithVariable()?.findValueWithVariable()?.let { values ->
            ArrayValue(
                values = values.map { createValue(it) },
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.findObjectValueWithVariable()?.findObjectFieldWithVariable()?.let { fields ->
            ObjectValue(
                fields = fields.mapNotNull { fieldContext ->
                    val name = fieldContext.findName()?.text
                    val value = fieldContext.findValueWithVariable()?.let { createValue(it) }

                    if (name != null && value != null) {
                        ObjectField(
                            name = name,
                            value = value,
                            sourceLocation = getSourceLocation(fieldContext),
                            comments = getComments(fieldContext),
                            ignoredChars = getIgnoredChars(fieldContext)
                        )
                    } else {
                        null
                    }
                },
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.findVariable()?.findName()?.text?.let {
            VariableReference(
                name = it,
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: Assert.assertShouldNeverHappen()

    private fun createValue(context: GraphqlParser.ValueContext): Value =
        context.BooleanValue()?.text?.toBooleanStrictOrNull()?.let {
            BooleanValue(
                value = it,
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.IntValue()?.text?.toBigInteger()?.let {
            IntValue(
                value = it,
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.FloatValue()?.text?.toBigDecimal()?.let {
            FloatValue(
                value = it,
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.StringValue()?.let { quotedString(it) }?.let {
            StringValue(
                value = it,
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.NullValue()?.let {
            NullValue(
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.findEnumValue()?.findEnumValueName()?.text?.let {
            EnumValue(
                name = it,
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.findArrayValue()?.findValue()?.let { values ->
            ArrayValue(
                values = values.map { createValue(it) },
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: context.findObjectValue()?.findObjectField()?.let { fields ->
            ObjectValue(
                fields = fields.mapNotNull { fieldContext ->
                    val name = fieldContext.findName()?.text
                    val value = fieldContext.findValue()?.let { createValue(it) }

                    if (name != null && value != null) {
                        ObjectField(
                            name = name,
                            value = value,
                            sourceLocation = getSourceLocation(fieldContext),
                            comments = getComments(fieldContext),
                            ignoredChars = getIgnoredChars(fieldContext)
                        )
                    } else {
                        null
                    }
                },
                sourceLocation = getSourceLocation(context),
                comments = getComments(context),
                ignoredChars = getIgnoredChars(context)
            )
        } ?: Assert.assertShouldNeverHappen()

    private fun quotedString(terminalNode: TerminalNode): String? {
        val multiLine = terminalNode.text.startsWith("\"\"\"")
        val strText = terminalNode.text
        val sourceLocation = AntlrHelper.createSourceLocation(
            multiSourceReader,
            terminalNode
        )

        return if (multiLine) {
            StringValueParsing.parseTripleQuotedString(strText)
        } else {
            StringValueParsing.parseSingleQuotedString(i18N, strText, sourceLocation ?: return null)
        }
    }

    private fun getIgnoredChars(context: ParserRuleContext): IgnoredChars {
        if (!parserOptions.isCaptureIgnoredChars) {
            return IgnoredChars.EMPTY
        }

        val start = context.start
        val tokenStartIndex = start?.tokenIndex
        val leftChannel = tokenStartIndex?.let { tokens.getHiddenTokensToLeft(it, CHANNEL_WHITESPACE) } ?: emptyList()
        val ignoredCharsLeft = mapTokenToIgnoredChar(leftChannel)
        val stop = context.stop
        val tokenStopIndex = stop?.tokenIndex
        val rightChannel = tokenStopIndex?.let { tokens.getHiddenTokensToRight(it, CHANNEL_WHITESPACE) } ?: emptyList()
        val ignoredCharsRight = mapTokenToIgnoredChar(rightChannel)

        return IgnoredChars(left = ignoredCharsLeft, right = ignoredCharsRight)
    }

    private fun mapTokenToIgnoredChar(tokens: List<Token?>): List<IgnoredChar> =
        tokens.mapNotNull { token -> token?.let { createIgnoredChar(it) } }

    private fun createIgnoredChar(token: Token): IgnoredChar {
        val kind: IgnoredChar.Kind = when (GraphqlLexer.VOCABULARY.getSymbolicName(token.type)) {
            "CR" -> IgnoredChar.Kind.CR
            "LF" -> IgnoredChar.Kind.LF
            "Tab" -> IgnoredChar.Kind.TAB
            "Comma" -> IgnoredChar.Kind.COMMA
            "Space" -> IgnoredChar.Kind.SPACE
            else -> IgnoredChar.Kind.OTHER
        }

        return IgnoredChar(value = token.text ?: "", kind = kind, sourceLocation = getSourceLocation(token))
    }

    private fun createDescription(context: GraphqlParser.DescriptionContext): Description? {
        val terminalNode = context.StringValue() ?: return null
        var content = terminalNode.text
        val multiLine = content.startsWith("\"\"\"")
        val sourceLocation = getSourceLocation(context) ?: return null

        content = if (multiLine) {
            StringValueParsing.parseTripleQuotedString(content)
        } else {
            StringValueParsing.parseSingleQuotedString(i18N, content, sourceLocation)
        }

        return Description(content = content, sourceLocation = sourceLocation, isMultiline = multiLine)
    }

    private fun getSourceLocation(parserRuleContext: ParserRuleContext): SourceLocation? =
        parserRuleContext.start?.let { getSourceLocation(it) }

    private fun getSourceLocation(token: Token): SourceLocation? {
        return if (parserOptions.isCaptureSourceLocation) {
            AntlrHelper.createSourceLocation(multiSourceReader, token)
        } else {
            null
        }
    }

    private fun getComments(context: ParserRuleContext): List<Comment> {
        if (!parserOptions.isCaptureLineComments) {
            return emptyList()
        }

        val start = context.start

        if (start != null) {
            val tokPos = start.tokenIndex
            val refChannel = tokens.getHiddenTokensToLeft(tokPos, CHANNEL_COMMENTS)

            if (refChannel != null) {
                return getCommentOnChannel(refChannel)
            }
        }

        return emptyList()
    }

    private fun getCommentOnChannel(refChannel: List<Token?>): List<Comment> {
        val comments = mutableListOf<Comment>()

        for (refTok in refChannel) {
            var text = refTok?.text ?: continue

            // we strip the leading hash # character, but we don't trim because we don't
            // know the "comment markup".  Maybe it's space sensitive, maybe it's not.  So
            // consumers can decide that
            text = text.replaceFirst("^#".toRegex(), "")

            val sourceAndLine = multiSourceReader.getSourceAndLineFromOverallLine(refTok.line)
            val column = refTok.charPositionInLine

            // graphql spec says line numbers start at 1
            val line = sourceAndLine.line + 1
            var sourceLocation: SourceLocation? = null

            if (parserOptions.isCaptureSourceLocation) {
                sourceLocation = SourceLocation(line, column, sourceAndLine.sourceName)
            }

            comments.add(Comment(content = text, sourceLocation = sourceLocation))
        }

        return comments.toList()
    }

    private fun getImplements(context: GraphqlParser.ImplementsInterfacesContext?): List<Type> {
        var implementsInterfacesContext = context
        val implements = mutableListOf<Type>()

        while (implementsInterfacesContext != null) {
            implementsInterfacesContext.findTypeName()
                ?.let { createTypeName(it) }
                ?.let { implements.add(0, it) }

            implementsInterfacesContext = implementsInterfacesContext.findImplementsInterfaces()
        }

        return implements
    }

    private fun <T : Node?> captureRuleContext(node: T?, context: ParserRuleContext?): T {
        nodeToRuleMap[node as Node] = context

        return node
    }
}
