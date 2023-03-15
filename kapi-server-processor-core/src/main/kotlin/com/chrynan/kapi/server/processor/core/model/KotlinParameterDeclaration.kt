package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Represents the declaration (not usage) of a parameter within a Kotlin function or constructor. A
 * [KotlinParameterDeclaration] is how the parameter is defined (ex: `val param: String = ""`) and not the usage or
 * call-site of passing a value as a parameter to a function or constructor (ex: `param = ""`).
 *
 * @property [name] The name of the parameter.
 * @property [type] The [KotlinTypeUsage] representing the Kotlin type of the parameter.
 * @property [isVararg] If this parameter is a vararg parameter (has the `vararg` modifier).
 * @property [isNoInline] If this parameter has the `noinline` modifier.
 * @property [isCrossInline] If this parameter has the `crossinline` modifier.
 * @property [isVal] If this parameter has the `val` modifier.
 * @property [isVar] If this parameter has the `var` modifier.
 * @property [hasDefaultValue] If this parameter has a default value.
 * @property [annotations] The annotations for this parameter.
 */
@Serializable
data class KotlinParameterDeclaration(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "type") val type: KotlinTypeUsage,
    @SerialName(value = "vararg") val isVararg: Boolean = false,
    @SerialName(value = "no_inline") val isNoInline: Boolean = false,
    @SerialName(value = "cross_inline") val isCrossInline: Boolean = false,
    @SerialName(value = "val") val isVal: Boolean = false,
    @SerialName(value = "var") val isVar: Boolean = false,
    @SerialName(value = "has_default_value") val hasDefaultValue: Boolean = false,
    @SerialName(value = "annotations") val annotations: List<KotlinAnnotationUsage> = emptyList(),
    @SerialName(value = "modifiers") val modifiers: List<KotlinParameterModifier> = emptyList()
)
