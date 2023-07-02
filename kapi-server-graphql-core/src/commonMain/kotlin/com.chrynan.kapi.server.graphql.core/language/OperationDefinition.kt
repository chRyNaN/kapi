package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Represents the [Definition] of a default operation type for a GraphQL [Document]. This can be a 'query', 'mutation',
 * or 'subscription' operation. Note that this is the operation definition (ex: when performing a query), not the
 * definition of the types associated with the operations.
 */
@Serializable
@SerialName(value = "OperationDefinition")
class OperationDefinition(
    @SerialName(value = "name") override val name: String,
    @SerialName(value = "operation") val operation: Operation,
    @SerialName(value = "variables") val variables: List<VariableDefinition> = emptyList(),
    @SerialName(value = "directives") override val directives: List<Directive> = emptyList(),
    @SerialName(value = "selection_set") override val selectionSet: SelectionSet,
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : Definition,
    NamedNode,
    SelectionSetContainer,
    DirectivesContainer {

    @Transient
    override val children: List<Node> = buildList {
        addAll(variables)
        addAll(directives)
        add(selectionSet)
    }

    fun copy(
        name: String = this.name,
        operation: Operation = this.operation,
        variables: List<VariableDefinition> = this.variables,
        directives: List<Directive> = this.directives,
        selectionSet: SelectionSet = this.selectionSet,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): OperationDefinition = OperationDefinition(
        name = name,
        operation = operation,
        variables = variables,
        directives = directives,
        selectionSet = selectionSet,
        sourceLocation = sourceLocation,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    override fun isContentEqualTo(node: Node): Boolean {
        if (this == node) return true
        if (node !is OperationDefinition) return false

        return name == node.name && operation == node.operation
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OperationDefinition) return false

        if (name != other.name) return false
        if (operation != other.operation) return false
        if (variables != other.variables) return false
        if (directives != other.directives) return false
        if (selectionSet != other.selectionSet) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + operation.hashCode()
        result = 31 * result + variables.hashCode()
        result = 31 * result + directives.hashCode()
        result = 31 * result + selectionSet.hashCode()
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "OperationDefinition(" +
                "name='$name', " +
                "operation=$operation, " +
                "variables=$variables, " +
                "directives=$directives, " +
                "selectionSet=$selectionSet, " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
