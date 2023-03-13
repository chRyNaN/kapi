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
 * Holds the relative paths to the individual endpoints and their operations. The path is appended to the URL from the
 * Server Object in order to construct the full URL. The Paths MAY be empty, due to [Access Control List (ACL)
 * constraints](https://spec.openapis.org/oas/v3.1.0#securityFiltering).
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * Assuming the following paths, the concrete definition, /pets/mine, will be matched first if used:
 *
 * ```
 * /pets/{petId}
 * /pets/mine
 * ```
 *
 * The following paths are considered identical and invalid:
 *
 * ```
 * /pets/{petId}
 * /pets/{name}
 * ```
 *
 * The following may lead to ambiguous resolution:
 *
 * ```
 * /{entity}/me
 * /books/{id}
 * ```
 *
 * ## Paths Object Example
 *
 * ```json
 * {
 *   "/pets": {
 *     "get": {
 *       "description": "Returns all pets from the system that the user has access to",
 *       "responses": {
 *         "200": {
 *           "description": "A list of pets.",
 *           "content": {
 *             "application/json": {
 *               "schema": {
 *                 "type": "array",
 *                 "items": {
 *                   "$ref": "#/components/schemas/pet"
 *                 }
 *               }
 *             }
 *           }
 *         }
 *       }
 *     }
 *   }
 * }
 * ```
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#paths-object)
 */
@Serializable(with = PathsSerializer::class)
data class Paths(
    @SerialName(value = "paths") val paths: Map<String, PathItem>? = null
)

/**
 * A [KSerializer] for the [Paths] class.
 */
internal object PathsSerializer : KSerializer<Paths> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Paths") {
        element<Map<String, PathItem>?>("paths")
    }

    override fun serialize(encoder: Encoder, value: Paths) {
        val jsonEncoder = (encoder as? JsonEncoder) ?: error("Paths object can be serialized only by JSON.")

        val jsonObject = buildJsonObject {
            value.paths?.forEach { entry ->
                put(
                    key = entry.key,
                    element = jsonEncoder.json.encodeToJsonElement(
                        serializer = PathItem.serializer(),
                        value = entry.value
                    )
                )
            }
        }

        encoder.encodeJsonElement(element = jsonObject)
    }

    override fun deserialize(decoder: Decoder): Paths {
        val jsonDecoder = (decoder as? JsonDecoder) ?: error("Responses object can be deserialized only by JSON.")
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject

        val paths = jsonObject.mapValues { entry ->
            jsonDecoder.json.decodeFromJsonElement(
                deserializer = PathItem.serializer(),
                element = entry.value
            )
        }

        return Paths(paths = paths)
    }
}
