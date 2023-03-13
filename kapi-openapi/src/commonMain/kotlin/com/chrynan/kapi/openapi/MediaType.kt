package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement

/**
 * Each Media Type Object provides schema and examples for the media type identified by its key.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * ## Media Type Object Example
 *
 * ```json
 * {
 *   "application/json": {
 *     "schema": {
 *          "$ref": "#/components/schemas/Pet"
 *     },
 *     "examples": {
 *       "cat" : {
 *         "summary": "An example of a cat",
 *         "value":
 *           {
 *             "name": "Fluffy",
 *             "petType": "Cat",
 *             "color": "White",
 *             "gender": "male",
 *             "breed": "Persian"
 *           }
 *       },
 *       "dog": {
 *         "summary": "An example of a dog with a cat's name",
 *         "value" :  {
 *           "name": "Puma",
 *           "petType": "Dog",
 *           "color": "Black",
 *           "gender": "Female",
 *           "breed": "Mixed"
 *         },
 *       "frog": {
 *           "$ref": "#/components/examples/frog-example"
 *         }
 *       }
 *     }
 *   }
 * }
 * ```
 *
 * @property [schema] The schema defining the content of the request, response, or parameter.
 * @property [example] Example of the media type. The example object SHOULD be in the correct format as specified by
 * the media type. The example field is mutually exclusive of the examples field. Furthermore, if referencing a schema
 * which contains an example, the example value SHALL override the example provided by the schema.
 * @property [examples] Examples of the media type. Each example object SHOULD match the media type and specified
 * schema if present. The examples field is mutually exclusive of the example field. Furthermore, if referencing a
 * schema which contains an example, the examples value SHALL override the example provided by the schema.
 * @property [encoding] A map between a property name and its encoding information. The key, being the property name,
 * MUST exist in the schema as a property. The encoding object SHALL only apply to requestBody objects when the media
 * type is multipart or application/x-www-form-urlencoded.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#media-type-object)
 */
@Serializable
data class MediaType(
    @SerialName(value = "schema") val schema: Schema? = null,
    @SerialName(value = "example") val example: JsonElement? = null,
    @SerialName(value = "examples") val examples: Map<String, ReferenceOrType<Example>>? = null,
    @SerialName(value = "encoding") val encoding: Map<String, Encoding>? = null
)
