package com.chrynan.kapi.openapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Describes a single response from an API Operation, including design-time, static links to operations based on the
 * response.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * ## Response Object Example
 *
 * ```json
 * {
 *   "description": "A complex object array response",
 *   "content": {
 *     "application/json": {
 *       "schema": {
 *         "type": "array",
 *         "items": {
 *           "$ref": "#/components/schemas/VeryComplexType"
 *         }
 *       }
 *     }
 *   }
 * }
 * ```
 *
 * @property [description] **REQUIRED**. A description of the response.
 * [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation.
 * @property [headers] Maps a header name to its definition.
 * [RFC7230](https://spec.openapis.org/oas/v3.1.0#bib-RFC7230) states header names are case-insensitive. If a response
 * header is defined with the name "Content-Type", it SHALL be ignored.
 * @property [content] A map containing descriptions of potential response payloads. The key is a media type or media
 * type range and the value describes it. For responses that match multiple keys, only the most specific key is
 * applicable. e.g. text/plain overrides text/\*
 * @property [links] A map of operations links that can be followed from the response. The key of the map is a short
 * name for the link, following the naming constraints of the names for Component Objects.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#response-object)
 */
@Serializable
data class Response(
    @SerialName(value = "description") val description: String,
    @SerialName(value = "headers") val headers: Map<String, ReferenceOrType<Header>>? = null,
    @SerialName(value = "content") val content: Map<String, MediaType>? = null,
    @SerialName(value = "links") val links: Map<String, ReferenceOrType<Link>>? = null
)
