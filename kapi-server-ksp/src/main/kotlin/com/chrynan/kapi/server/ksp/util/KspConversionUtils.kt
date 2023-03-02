package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.symbol.*

internal val KSDeclaration.kotlinName: KotlinName
    get() = this.qualifiedName?.toKotlinName() ?: this.simpleName.toKotlinName()

internal val KSType.kotlinName: KotlinName
    get() = this.declaration.kotlinName

internal fun KSName.toKotlinName(): KotlinName =
    KotlinName(
        full = this.asString(),
        short = this.getShortName(),
        qualifier = this.getQualifier()
    )

internal fun Variance.toKotlinGenericVariance(): KotlinGenericVariance =
    when (this) {
        Variance.STAR -> KotlinGenericVariance.STAR
        Variance.CONTRAVARIANT -> KotlinGenericVariance.CONTRAVARIANT
        Variance.COVARIANT -> KotlinGenericVariance.COVARIANT
        Variance.INVARIANT -> KotlinGenericVariance.INVARIANT
    }

internal fun KSTypeArgument.toKotlinTypeArgument(): KotlinTypeArgument =
    KotlinTypeArgument(
        variance = this.variance.toKotlinGenericVariance(),
        type = this.type?.toKotlinTypeUsage()
    )

internal fun KSTypeReference.toKotlinTypeUsage(): KotlinTypeUsage {
    val type = this.resolve()

    return KotlinTypeUsage(
        name = type.kotlinName,
        typeArguments = this.element?.typeArguments?.map { it.toKotlinTypeArgument() }?.toList() ?: emptyList(),
        isNullable = type.isMarkedNullable,
        annotations = type.annotations.map { it.toKotlinAnnotation() }.toList()
    )
}

internal fun KSValueParameter.toKotlinParameterDeclaration(): KotlinParameterDeclaration =
    KotlinParameterDeclaration(
        name = this.name?.asString() ?: "",
        type = this.type.toKotlinTypeUsage(),
        isVararg = this.isVararg,
        isNoInline = this.isNoInline,
        isCrossInline = this.isCrossInline,
        isVal = this.isVal,
        isVar = this.isVar,
        hasDefaultValue = this.hasDefault,
        annotations = this.annotations.map { it.toKotlinAnnotation() }.toList()
    )
