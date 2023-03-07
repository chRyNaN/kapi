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

internal fun CodeBlock.Builder.addStatement(codeBlock: CodeBlock): CodeBlock.Builder = apply {
    add("«")
    add(codeBlock)
    add("\n»")
}
