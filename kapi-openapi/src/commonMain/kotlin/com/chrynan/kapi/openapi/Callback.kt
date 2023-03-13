package com.chrynan.kapi.openapi

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject

/**
 * A map of possible out-of band callbacks related to the parent operation. Each value in the map is a Path Item Object
 * that describes a set of requests that may be initiated by the API provider and the expected responses. The key value
 * used to identify the path item object is an expression, evaluated at runtime, that identifies a URL to use for the
 * callback operation.
 *
 * To describe incoming requests from the API provider independent from another API call, use the webhooks field.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#callback-object)
 */
@Serializable(with = CallbackSerializer::class)
data class Callback(
    @SerialName(value = "paths") val paths: Map<String, ReferenceOrType<PathItem>>? = null
)

/**
 * A [KSerializer] for the [Callback] class.
 */
internal object CallbackSerializer : KSerializer<Callback> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Paths") {
        element<Map<String, PathItem>?>("paths")
    }

    override fun serialize(encoder: Encoder, value: Callback) {
        val jsonEncoder = (encoder as? JsonEncoder) ?: error("Paths object can be serialized only by JSON.")

        val jsonObject = buildJsonObject {
            value.paths?.forEach { entry ->
                put(
                    key = entry.key,
                    element = jsonEncoder.json.encodeToJsonElement(
                        serializer = ReferenceOrType.serializer(PathItem.serializer()),
                        value = entry.value
                    )
                )
            }
        }

        encoder.encodeJsonElement(element = jsonObject)
    }

    override fun deserialize(decoder: Decoder): Callback {
        val jsonDecoder = (decoder as? JsonDecoder) ?: error("Responses object can be deserialized only by JSON.")
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject

        val paths = jsonObject.mapValues { entry ->
            jsonDecoder.json.decodeFromJsonElement(
                deserializer = ReferenceOrType.serializer(PathItem.serializer()),
                element = entry.value
            )
        }

        return Callback(paths = paths)
    }
}
