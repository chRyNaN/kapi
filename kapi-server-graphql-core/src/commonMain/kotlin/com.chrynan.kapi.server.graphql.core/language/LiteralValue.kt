package com.chrynan.kapi.server.graphql.core.language

import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.*

/**
 * Represents a [Value] that is an already resolved literal. These values can be referenced from a variable.
 * [VariableReference] instances can only point to [VariableDefinition]s that contain values of [LiteralValue]
 * subclasses (they cannot point to further variable references: see note further in the documentation of this
 * interface). The following is taken from the
 * [GraphQL Specification](https://spec.graphql.org/October2021/#sec-Variables-Are-Input-Types):
 *
 * > Variables can only be input types. Objects, unions, and interfaces cannot be used as inputs.
 *
 * That means [VariableDefinition]s can only be GraphQL input types (scalars, enums, or input objects), and, therefore,
 * [VariableReference]s can only reference values of GraphQL input types. These referenced [Value]s are scoped to this
 * interface.
 *
 * **Note:** It is unclear in the specification whether `null` and array types are supported, though it seems that they
 * are. Also, it is unclear whether values can have nested variable references, such as an array type with values that
 * are themselves variable references. I have decided not to support the nested variable references, even though the
 * specification wasn't clear about it, doing so will add lots of complications.
 */
@Serializable
sealed interface LiteralValue : Value {

    /**
     * The [JsonElement] representation of this [LiteralValue]. A [LiteralValue] does not take in variables to resolve
     * its [JsonElement] representation. This value defaults to calling the [Value.element] function with an empty
     * [Map] of variables.
     */
    @Transient
    val literalElement: JsonElement get() = element()
}

/**
 * Creates a [LiteralValue] using the provided values. This constructor function will create the appropriate
 * [LiteralValue] instance depending on the provided [JsonElement] [value].
 *
 * **Note:** That most values can be appropriately construed by the [JsonElement] instance, however, enum and string
 * values cannot be differentiated. As such, use the [isEnum] parameter to state whether this should be considered an
 * enum or not, if the provided [JsonElement] [value] is a [String] value. However, this field does not propagate
 * inwards to nested fields of arrays or objects.
 */
fun LiteralValue(
    value: JsonElement,
    isEnum: Boolean = false,
    sourceLocation: SourceLocation? = null,
    comments: List<Comment> = emptyList(),
    ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    additionalData: Map<String, String> = emptyMap()
): LiteralValue =
    when (value) {
        is JsonNull -> NullValue(
            sourceLocation = sourceLocation,
            comments = comments,
            ignoredChars = ignoredChars,
            additionalData = additionalData
        )

        is JsonPrimitive -> {
            value.booleanOrNull?.let {
                BooleanValue(
                    value = it,
                    sourceLocation = sourceLocation,
                    comments = comments,
                    ignoredChars = ignoredChars,
                    additionalData = additionalData
                )
            } ?: value.intOrNull?.let {
                IntValue(
                    value = it.toBigInteger(),
                    sourceLocation = sourceLocation,
                    comments = comments,
                    ignoredChars = ignoredChars,
                    additionalData = additionalData
                )
            } ?: value.floatOrNull?.let {
                FloatValue(
                    value = it.toBigDecimal(),
                    sourceLocation = sourceLocation,
                    comments = comments,
                    ignoredChars = ignoredChars,
                    additionalData = additionalData
                )
            } ?: value.doubleOrNull?.let {
                FloatValue(
                    value = it.toBigDecimal(),
                    sourceLocation = sourceLocation,
                    comments = comments,
                    ignoredChars = ignoredChars,
                    additionalData = additionalData
                )
            } ?: if (isEnum) {
                EnumValue(
                    name = value.content,
                    sourceLocation = sourceLocation,
                    comments = comments,
                    ignoredChars = ignoredChars,
                    additionalData = additionalData
                )
            } else {
                StringValue(
                    value = value.content,
                    sourceLocation = sourceLocation,
                    comments = comments,
                    ignoredChars = ignoredChars,
                    additionalData = additionalData
                )
            }
        }

        is JsonArray -> ArrayValue(
            values = value.map { LiteralValue(value = it) },
            sourceLocation = sourceLocation,
            comments = comments,
            ignoredChars = ignoredChars,
            additionalData = additionalData
        )

        is JsonObject -> ObjectValue(
            fields = value.entries.map {
                ObjectField(
                    name = it.key,
                    value = LiteralValue(value = it.value)
                )
            },
            sourceLocation = sourceLocation,
            comments = comments,
            ignoredChars = ignoredChars,
            additionalData = additionalData
        )
    }
