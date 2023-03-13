package com.chrynan.kapi.openapi

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

/**
 * A container for the expected responses of an operation. The container maps a HTTP response code to the expected
 * response.
 *
 * The documentation is not necessarily expected to cover all possible HTTP response codes because they may not be
 * known in advance. However, documentation is expected to cover a successful operation response and any known errors.
 *
 * The default MAY be used as a default response object for all HTTP codes that are not covered individually by the
 * Responses Object.
 *
 * The Responses Object MUST contain at least one response code, and if only one response code is provided it SHOULD be
 * the response for a successful operation call.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * ## Responses Object Example
 *
 * ```json
 * {
 *   "200": {
 *     "description": "a pet to be returned",
 *     "content": {
 *       "application/json": {
 *         "schema": {
 *           "$ref": "#/components/schemas/Pet"
 *         }
 *       }
 *     }
 *   },
 *   "default": {
 *     "description": "Unexpected error",
 *     "content": {
 *       "application/json": {
 *         "schema": {
 *           "$ref": "#/components/schemas/ErrorModel"
 *         }
 *       }
 *     }
 *   }
 * }
 * ```
 *
 * @property [default] The documentation of responses other than the ones declared for specific HTTP response codes.
 * Use this field to cover undeclared responses.
 * @property [responses] Any HTTP status code can be used as the property name, but only one property per code, to
 * describe the expected response for that HTTP status code. This field MUST be enclosed in quotation marks
 * (for example, “200”) for compatibility between JSON and YAML. To define a range of response codes, this field MAY
 * contain the uppercase wildcard character X. For example, 2XX represents all response codes between [200-299]. Only
 * the following range definitions are allowed: 1XX, 2XX, 3XX, 4XX, and 5XX. If a response is defined using an explicit
 * code, the explicit code definition takes precedence over the range definition for that code.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#responses-object)
 */
@Serializable(with = ResponsesSerializer::class)
data class Responses(
    @SerialName(value = "default") val default: ReferenceOrType<Response>? = null,
    @SerialName(value = "responses") val responses: Map<String, ReferenceOrType<Response>>? = null
) {

    companion object {

        internal const val PROPERTY_NAME_DEFAULT = "default"
    }
}

/**
 * A [KSerializer] for the [Responses] class.
 */
internal object ResponsesSerializer : KSerializer<Responses> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Responses") {
        element<ReferenceOrType<Response>?>("default")
        element<JsonElement>("responses")
    }

    override fun serialize(encoder: Encoder, value: Responses) {
        val jsonEncoder = (encoder as? JsonEncoder) ?: error("Responses object can be serialized only by JSON.")

        val jsonObject = buildJsonObject {
            value.default?.let { default ->
                put(
                    key = Responses.PROPERTY_NAME_DEFAULT,
                    element = jsonEncoder.json.encodeToJsonElement(
                        serializer = ReferenceOrType.serializer(Response.serializer()),
                        value = default
                    )
                )
            }

            value.responses?.let { responses ->
                responses.forEach { entry ->
                    put(
                        key = entry.key,
                        element = jsonEncoder.json.encodeToJsonElement(
                            serializer = ReferenceOrType.serializer(Response.serializer()),
                            value = entry.value
                        )
                    )
                }
            }
        }

        encoder.encodeJsonElement(element = jsonObject)
    }

    override fun deserialize(decoder: Decoder): Responses {
        val jsonDecoder = (decoder as? JsonDecoder) ?: error("Responses object can be deserialized only by JSON.")
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject

        val default = try {
            jsonObject[Responses.PROPERTY_NAME_DEFAULT]?.let {
                jsonDecoder.json.decodeFromJsonElement(
                    deserializer = ReferenceOrType.serializer(Response.serializer()),
                    element = it
                )
            }
        } catch (_: Exception) {
            null
        }

        val responsesJson = jsonObject - Responses.PROPERTY_NAME_DEFAULT

        val responses = responsesJson.mapValues { entry ->
            try {
                jsonDecoder.json.decodeFromJsonElement(
                    deserializer = ReferenceOrType.serializer(Response.serializer()),
                    element = entry.value
                )
            } catch (_: Exception) {
                null
            }
        }.filterValues { it != null }
            .takeIf { it.isNotEmpty() }

        @Suppress("UNCHECKED_CAST")
        return Responses(
            default = default,
            responses = responses as Map<String, ReferenceOrType<Response>>?
        )
    }
}
