@file:Suppress("unused")

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
 * @property [annotations] Any annotations applied to this [KotlinTypeUsage].
 */
@Serializable
data class KotlinTypeUsage(
    @SerialName(value = "name") val name: KotlinName,
    @SerialName(value = "type_arguments") val typeArguments: List<KotlinTypeArgument> = emptyList(),
    @SerialName(value = "nullable") val isNullable: Boolean = false,
    @SerialName(value = "annotations") val annotations: List<KotlinAnnotationUsage> = emptyList()
)

/**
 * Determines if this type is a Kotlin Boolean.
 */
val KotlinTypeUsage.isBoolean: Boolean
    get() = name.full == "kotlin.Boolean"

/**
 * Determines if this type is a Kotlin Byte.
 */
val KotlinTypeUsage.isByte: Boolean
    get() = name.full == "kotlin.Byte"

/**
 * Determines if this type is a Kotlin Short.
 */
val KotlinTypeUsage.isShort: Boolean
    get() = name.full == "kotlin.Short"

/**
 * Determines if this type is a Kotlin Int.
 */
val KotlinTypeUsage.isInt: Boolean
    get() = name.full == "kotlin.Int"

/**
 * Determines if this type is a Kotlin Long.
 */
val KotlinTypeUsage.isLong: Boolean
    get() = name.full == "kotlin.Long"

/**
 * Determines if this type is a Kotlin Float.
 */
val KotlinTypeUsage.isFloat: Boolean
    get() = name.full == "kotlin.Float"

/**
 * Determines if this type is a Kotlin Double.
 */
val KotlinTypeUsage.isDouble: Boolean
    get() = name.full == "kotlin.Double"

/**
 * Determines if this type is a Kotlin Char.
 */
val KotlinTypeUsage.isChar: Boolean
    get() = name.full == "kotlin.Char"

/**
 * Determines if this type is a Kotlin String.
 */
val KotlinTypeUsage.isString: Boolean
    get() = name.full == "kotlin.String"

/**
 * Determines if this type is a Kotlin Unit.
 */
val KotlinTypeUsage.isUnit: Boolean
    get() = name.full == "kotlin.Unit"

/**
 * Determines if this type is a Kotlin Nothing.
 */
val KotlinTypeUsage.isNothing: Boolean
    get() = name.full == "kotlin.Nothing"

/**
 * Determines if this type is a Kapi Response.
 */
val KotlinTypeUsage.isResponse: Boolean
    get() = name.full == "com.chrynan.kapi.core.Response"

/**
 * Determines if this type is a Ktor HttpResponse.
 */
val KotlinTypeUsage.isHttpResponse: Boolean
    get() = name.full == "io.ktor.client.statement.HttpResponse"
