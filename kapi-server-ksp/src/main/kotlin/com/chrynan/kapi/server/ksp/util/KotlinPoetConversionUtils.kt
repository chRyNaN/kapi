package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.server.processor.core.model.KotlinTypeUsage
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName

/**
 * Creates a [TypeName] instance from this [KotlinTypeUsage].
 */
val KotlinTypeUsage.typeName: TypeName
    get() = ClassName.bestGuess(name.full).copy(nullable = this.isNullable)
