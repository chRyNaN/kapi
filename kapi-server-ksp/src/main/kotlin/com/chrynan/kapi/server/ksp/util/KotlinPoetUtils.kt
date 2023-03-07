package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.server.processor.core.model.KotlinTypeUsage
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName

/**
 * Creates a [TypeName] instance from this [KotlinTypeUsage].
 */
internal val KotlinTypeUsage.typeName: TypeName
    get() = ClassName.bestGuess(name.full).copy(nullable = this.isNullable)

internal fun CodeBlock.Builder.controlFlow(
    value: String,
    blockWithoutControlFlow: Boolean = false,
    vararg args: Any?,
    block: CodeBlock.Builder.() -> Unit
): CodeBlock.Builder {
    val builder = this

    if (!blockWithoutControlFlow) {
        builder.beginControlFlow(controlFlow = value, args = args)
    }

    builder.block()

    if (!blockWithoutControlFlow) {
        builder.endControlFlow()
    }

    return builder
}

internal fun CodeBlock.Builder.addPropertyDeclaration(
    propertyName: String,
    propertyTypeName: TypeName,
    assignment: String? = null,
    isNullable: Boolean = false,
    isVar: Boolean = false,
    vararg assignmentArgs: Any?
): CodeBlock.Builder {
    val propertyModifier = if (isVar) "var" else "val"
    val declaration = if (assignment == null) {
        "$propertyModifier %S: %T?"
    } else {
        "$propertyModifier %S: %T? = $assignment"
    }

    return this.addStatement(declaration, propertyName, propertyTypeName.copy(nullable = isNullable), assignmentArgs)
}
