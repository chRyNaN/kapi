package com.chrynan.kapi.openapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Describes a single request body.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * @property [description] A brief description of the request body. This could contain examples of use.
 * [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation.
 * @property [content] **REQUIRED**. The content of the request body. The key is a media type or media type range and
 * the value describes it. For requests that match multiple keys, only the most specific key is applicable. e.g.
 * text/plain overrides text/\*
 * @property [required] Determines if the request body is required in the request. Defaults to false.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#request-body-object)
 */
@Serializable
data class RequestBody(
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "content") val content: Map<String, MediaType>,
    @SerialName(value = "required") val required: Boolean = false
)
