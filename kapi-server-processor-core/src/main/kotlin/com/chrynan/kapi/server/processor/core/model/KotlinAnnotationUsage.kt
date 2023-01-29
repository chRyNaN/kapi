@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Represents the use of a Kotlin Annotation.
 *
 * @property [type] The [KotlinTypeUsage] representing this annotation class.
 * @property [arguments] The arguments explicitly specified on this annotation.
 * @property [defaultArguments] The default arguments that are not explicitly specified on the use-site, but are
 * defaults within the annotation class.
 */
@Serializable
data class KotlinAnnotationUsage(
    @SerialName(value = "type") val type: KotlinTypeUsage,
    @SerialName(value = "arguments") val arguments: List<Argument> = emptyList(),
    @SerialName(value = "default_arguments") val defaultArguments: List<Argument> = emptyList()
) {

    /**
     * Represents an argument on a [KotlinAnnotationUsage] use-site.
     *
     * @property [name] The property name of this named argument, or `null` otherwise.
     * @property [isSpread] Whether this argument is a spread argument (i.e., has a "*" in front of the argument).
     * @property [type] The [KotlinTypeUsage] representing this argument type.
     */
    @Serializable
    data class Argument(
        @SerialName(value = "name") val name: String? = null,
        @SerialName(value = "spread") val isSpread: Boolean = false,
        @SerialName(value = "type") val type: KotlinTypeUsage? = null,
        @SerialName(value = "value") val value: Value? = null
    ) {

        /**
         * The value for a [KotlinAnnotationUsage.Argument].
         *
         * @property [value] The value provided, either explicitly or by default, for this annotation argument.
         */
        @Serializable
        sealed class Value {

            @SerialName(value = "value")
            abstract val value: Any?
        }

        @Serializable
        @SerialName(value = "boolean")
        data class BooleanValue(
            @SerialName(value = "value") override val value: Boolean? = null
        ) : Value()

        @Serializable
        @SerialName(value = "byte")
        data class ByteValue(
            @SerialName(value = "value") override val value: Byte? = null
        ) : Value()

        @Serializable
        @SerialName(value = "short")
        data class ShortValue(
            @SerialName(value = "value") override val value: Short? = null
        ) : Value()

        @Serializable
        @SerialName(value = "int")
        data class IntValue(
            @SerialName(value = "value") override val value: Int? = null
        ) : Value()

        @Serializable
        @SerialName(value = "long")
        data class LongValue(
            @SerialName(value = "value") override val value: Long? = null
        ) : Value()

        @Serializable
        @SerialName(value = "float")
        data class FloatValue(
            @SerialName(value = "value") override val value: Float? = null
        ) : Value()

        @Serializable
        @SerialName(value = "double")
        data class DoubleValue(
            @SerialName(value = "value") override val value: Double? = null
        ) : Value()

        @Serializable
        @SerialName(value = "char")
        data class CharValue(
            @SerialName(value = "value") override val value: Char? = null
        ) : Value()

        @Serializable
        @SerialName(value = "string")
        data class StringValue(
            @SerialName(value = "value") override val value: String? = null
        ) : Value()

        @Serializable
        @SerialName(value = "class")
        data class ClassValue(
            @SerialName(value = "value") override val value: KotlinName? = null
        ) : Value()

        @Serializable
        @SerialName(value = "annotation")
        data class AnnotationValue(
            @SerialName(value = "value") override val value: KotlinAnnotationUsage? = null
        ) : Value()

        @Serializable
        @SerialName(value = "array")
        data class ArrayValue(
            @SerialName(value = "value") override val value: List<Value>? = null
        ) : Value()

        @Serializable
        @SerialName(value = "complex")
        data class ComplexValue(
            @SerialName(value = "value") override val value: String? = null
        ) : Value()
    }
}
