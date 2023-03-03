@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import io.ktor.http.content.*
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
 * Determines if this type is a Kotlin Array.
 */
val KotlinTypeUsage.isArray: Boolean
    get() = name.full == "kotlin.Array"

/**
 * Determines if this type is a Kotlin ByteArray.
 */
val KotlinTypeUsage.isByteArray: Boolean
    get() = name.full == "kotlin.ByteArray"

/**
 * Determines if this type is a Kotlin List.
 */
val KotlinTypeUsage.isList: Boolean
    get() = name.full == "kotlin.collections.List"

/**
 * Determines if this type is a Kotlin Collection.
 */
val KotlinTypeUsage.isCollection: Boolean
    get() = name.full == "kotlin.collections.Collection"

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

/**
 * Determines if this type is a Ktor ApplicationCall.
 */
val KotlinTypeUsage.isApplicationCall: Boolean
    get() = name.full == "io.ktor.server.application.ApplicationCall"

/**
 * Determines if this type is a Ktor PartData.
 */
val KotlinTypeUsage.isPartData: Boolean
    get() = name.full.startsWith("io.ktor.http.content.PartData")

/**
 * Determines if this type is a Ktor Input.
 */
val KotlinTypeUsage.isInput: Boolean
    get() = name.full == "io.ktor.utils.io.core.Input"

/**
 * Determines if this type is a Ktor MultiPartData.
 */
val KotlinTypeUsage.isMultiPartData: Boolean
    get() = name.full == "io.ktor.http.content.MultiPartData"

/**
 * Determines if this type is a Ktor Route.
 */
val KotlinTypeUsage.isRoute: Boolean
    get() = name.full == "io.ktor.server.routing.Route"

/**
 * Determines if this type is a Ktor ByteReadChannel.
 */
val KotlinTypeUsage.isByteReadChannel: Boolean
    get() = name.full == "io.ktor.utils.io.ByteReadChannel"

/**
 * Determines if this type is a Ktor Parameters.
 */
val KotlinTypeUsage.isParameters: Boolean
    get() = name.full == "io.ktor.http.Parameters"

/**
 * Determines if this type is a java.io.InputStream.
 */
val KotlinTypeUsage.isInputStream: Boolean
    get() = name.full == "java.io.InputStream"

/**
 * Determines if this type can be converted from path and query parameters of an HTTP request using the Ktor
 * io.ktor.util.converters.DefaultConversionService.
 */
val KotlinTypeUsage.isConvertibleByDefaultConversionService: Boolean
    get() = this.isBoolean || this.isShort || this.isInt || this.isLong || this.isFloat || this.isDouble || this.isChar || this.isString
