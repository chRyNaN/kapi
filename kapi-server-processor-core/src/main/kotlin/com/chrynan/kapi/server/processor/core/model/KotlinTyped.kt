package com.chrynan.kapi.server.processor.core.model

/**
 * Represents a Kotlin type (usage or definition).
 *
 * @see [KotlinTypeUsage] for usage of a Kotlin type.
 * @see [KotlinTypeDeclaration] for a declaration of a Kotlin type.
 */
interface KotlinTyped {

    /**
     * The [KotlinName] of the type.
     */
    val name: KotlinName

    /**
     * Any annotations applied to this type.
     */
    val annotations: List<KotlinAnnotationUsage>
}

/**
 * Determines if this type is a Kotlin Boolean.
 */
val KotlinTyped.isBoolean: Boolean
    get() = name.full == "kotlin.Boolean"

/**
 * Determines if this type is a Kotlin Byte.
 */
val KotlinTyped.isByte: Boolean
    get() = name.full == "kotlin.Byte"

/**
 * Determines if this type is a Kotlin Short.
 */
val KotlinTyped.isShort: Boolean
    get() = name.full == "kotlin.Short"

/**
 * Determines if this type is a Kotlin Int.
 */
val KotlinTyped.isInt: Boolean
    get() = name.full == "kotlin.Int"

/**
 * Determines if this type is a Kotlin Long.
 */
val KotlinTyped.isLong: Boolean
    get() = name.full == "kotlin.Long"

/**
 * Determines if this type is a Kotlin UByte.
 */
val KotlinTyped.isUByte: Boolean
    get() = name.full == "kotlin.UByte"

/**
 * Determines if this type is a Kotlin UShort.
 */
val KotlinTyped.isUShort: Boolean
    get() = name.full == "kotlin.UShort"

/**
 * Determines if this type is a Kotlin UInt.
 */
val KotlinTyped.isUInt: Boolean
    get() = name.full == "kotlin.UInt"

/**
 * Determines if this type is a Kotlin ULong.
 */
val KotlinTyped.isULong: Boolean
    get() = name.full == "kotlin.ULong"

/**
 * Determines if this type is a Kotlin Float.
 */
val KotlinTyped.isFloat: Boolean
    get() = name.full == "kotlin.Float"

/**
 * Determines if this type is a Kotlin Double.
 */
val KotlinTyped.isDouble: Boolean
    get() = name.full == "kotlin.Double"

/**
 * Determines if this type is a Kotlin Char.
 */
val KotlinTyped.isChar: Boolean
    get() = name.full == "kotlin.Char"

/**
 * Determines if this type is a Kotlin String.
 */
val KotlinTyped.isString: Boolean
    get() = name.full == "kotlin.String"

/**
 * Determines if this type is a Kotlin CharSequence.
 */
val KotlinTyped.isCharSequence: Boolean
    get() = name.full == "kotlin.CharSequence"

/**
 * Determines if this type is a Kotlin Unit.
 */
val KotlinTyped.isUnit: Boolean
    get() = name.full == "kotlin.Unit"

/**
 * Determines if this type is a Kotlin Nothing.
 */
val KotlinTyped.isNothing: Boolean
    get() = name.full == "kotlin.Nothing"

/**
 * Determines if this type is a Kotlin Array.
 */
val KotlinTyped.isArray: Boolean
    get() = name.full == "kotlin.Array"

/**
 * Determines if this type is a Kotlin [BooleanArray].
 */
val KotlinTyped.isBooleanArray: Boolean
    get() = name.full == "kotlin.BooleanArray"

/**
 * Determines if this type is a Kotlin ByteArray.
 */
val KotlinTyped.isByteArray: Boolean
    get() = name.full == "kotlin.ByteArray"

/**
 * Determines if this type is a Kotlin [CharArray].
 */
val KotlinTyped.isCharArray: Boolean
    get() = name.full == "kotlin.CharArray"

/**
 * Determines if this type is a Kotlin [ShortArray].
 */
val KotlinTyped.isShortArray: Boolean
    get() = name.full == "kotlin.ShortArray"

/**
 * Determines if this type is a Kotlin [IntArray].
 */
val KotlinTyped.isIntArray: Boolean
    get() = name.full == "kotlin.IntArray"

/**
 * Determines if this type is a Kotlin [LongArray].
 */
val KotlinTyped.isLongArray: Boolean
    get() = name.full == "kotlin.LongArray"

/**
 * Determines if this type is a Kotlin [FloatArray].
 */
val KotlinTyped.isFloatArray: Boolean
    get() = name.full == "kotlin.FloatArray"

/**
 * Determines if this type is a Kotlin [DoubleArray].
 */
val KotlinTyped.isDoubleArray: Boolean
    get() = name.full == "kotlin.DoubleArray"

/**
 * Determines if this type is a Kotlin List.
 */
val KotlinTyped.isList: Boolean
    get() = name.full == "kotlin.collections.List"

/**
 * Determines if this type is a Kotlin Set.
 */
val KotlinTyped.isSet: Boolean
    get() = name.full == "kotlin.collections.Set"

/**
 * Determines if this type is a Kotlin Collection.
 */
val KotlinTyped.isCollection: Boolean
    get() = name.full == "kotlin.collections.Collection"

/**
 * Determines if this [KotlinTyped] instance is a Kotlin [Collection] type or an immediate descendant of [Collection],
 * [List], or [Set] if that information is available.
 */
val KotlinTyped.isSupportedCollectionType: Boolean
    get() = if (this is KotlinTypeDeclaration) {
        this.isCollection || this.isList || this.isSet || this.superTypes.any { it.isSupportedCollectionType }
    } else {
        this.isCollection || this.isList || this.isSet
    }

/**
 * Determines if this [KotlinTyped] instance is a Kotlin [Array] type or an immediate descendant of [Array].
 */
val KotlinTyped.isSupportedArrayType: Boolean
    get() = if (this is KotlinTypeDeclaration) {
        this.isArray || this.isBooleanArray || this.isCharArray || this.isShortArray || this.isIntArray || this.isLongArray || this.isFloatArray || this.isDoubleArray || this.superTypes.any { it.isSupportedArrayType }
    } else {
        this.isArray || this.isBooleanArray || this.isCharArray || this.isShortArray || this.isIntArray || this.isLongArray || this.isFloatArray || this.isDoubleArray
    }

/**
 * Determines if this type is a Kapi Response.
 */
val KotlinTyped.isResponse: Boolean
    get() = name.full == "com.chrynan.kapi.core.Response"

/**
 * Determines if this type is a Ktor HttpResponse.
 */
@Suppress("unused")
val KotlinTyped.isHttpResponse: Boolean
    get() = name.full == "io.ktor.client.statement.HttpResponse"

/**
 * Determines if this type is a Ktor ApplicationCall.
 */
val KotlinTyped.isApplicationCall: Boolean
    get() = name.full == "io.ktor.server.application.ApplicationCall"

/**
 * Determines if this type is a Ktor PartData.
 */
val KotlinTyped.isPartData: Boolean
    get() = name.full.startsWith("io.ktor.http.content.PartData")

/**
 * Determines if this type is a Ktor Input.
 */
val KotlinTyped.isInput: Boolean
    get() = name.full == "io.ktor.utils.io.core.Input"

/**
 * Determines if this type is a Ktor MultiPartData.
 */
val KotlinTyped.isMultiPartData: Boolean
    get() = name.full == "io.ktor.http.content.MultiPartData"

/**
 * Determines if this type is a Ktor Route.
 */
val KotlinTyped.isRoute: Boolean
    get() = name.full == "io.ktor.server.routing.Route"

/**
 * Determines if this type is a Ktor ByteReadChannel.
 */
val KotlinTyped.isByteReadChannel: Boolean
    get() = name.full == "io.ktor.utils.io.ByteReadChannel"

/**
 * Determines if this type is a Ktor Parameters.
 */
val KotlinTyped.isParameters: Boolean
    get() = name.full == "io.ktor.http.Parameters"

/**
 * Determines if this type is a java.io.InputStream.
 */
val KotlinTyped.isInputStream: Boolean
    get() = name.full == "java.io.InputStream"

/**
 * Determines if this type can be converted from path and query parameters of an HTTP request using the Ktor
 * io.ktor.util.converters.DefaultConversionService.
 */
val KotlinTyped.isConvertibleByDefaultConversionService: Boolean
    get() = this.isBoolean || this.isShort || this.isInt || this.isLong || this.isFloat || this.isDouble || this.isChar || this.isString
