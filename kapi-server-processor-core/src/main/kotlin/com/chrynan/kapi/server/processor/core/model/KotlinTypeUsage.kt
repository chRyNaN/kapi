package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Represents the usage (not declaration or definition) of a Kotlin type. For instance, a return type for a function
 * can be represented as a [KotlinTypeUsage]. This can be considered as the usage of an already defined (a.k.a.
 * declared) type.
 *
 * @property [name] The [KotlinName] of the type.
 * @property [typeArguments] The generic type arguments provided to this type at its use-site.
 * @property [isNullable] Whether the type is nullable, meaning there is a trailing question mark at its use-site.
 */
@Serializable
data class KotlinTypeUsage(
    @SerialName(value = "name") val name: KotlinName,
    @SerialName(value = "type_arguments") val typeArguments: List<KotlinTypeArgument> = emptyList(),
    @SerialName(value = "nullable") val isNullable: Boolean = false
)
