package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.server.processor.core.model.KotlinName
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSName

internal val KSDeclaration.kotlinName: KotlinName
    get() = this.qualifiedName?.toKotlinName() ?: this.simpleName.toKotlinName()

internal fun KSName.toKotlinName(): KotlinName =
    KotlinName(
        full = this.asString(),
        short = this.getShortName(),
        qualifier = this.getQualifier()
    )
