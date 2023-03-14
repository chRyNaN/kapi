package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a Kotlin type parameter. Note that this represents a type parameter and not a type argument. A type
 * parameter is a generic type parameter definition specified on a component like a function or class, whereas a type
 * argument is a generic type argument usage provided at the call site. A [KotlinTypeParameter] is a variable used at
 * the definition of a component, whereas the a [KotlinTypeArgument] is a value provided to a generic component.
 *
 * @property [name] The name of this type parameter.
 * @property [variance] The [KotlinGenericVariance] of this type parameter.
 * @property [isReified] Whether this type parameter contains the reified modifier.
 * @property [bounds] The upper bounds of this type parameter.
 *
 * @see [KotlinTypeArgument] for a type argument instead of a type parameter.
 */
@Serializable
data class KotlinTypeParameter(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "variance") val variance: KotlinGenericVariance,
    @SerialName(value = "reified") val isReified: Boolean = false,
    @SerialName(value = "bounds") val bounds: List<KotlinTypeUsage> = emptyList()
)
