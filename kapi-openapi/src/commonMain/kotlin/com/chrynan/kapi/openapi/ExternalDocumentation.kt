package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Allows referencing an external resource for extended documentation.
 *
 * ## External Documentation Object Example
 *
 * ```json
 * {
 *   "description": "Find more info here",
 *   "url": "https://example.com"
 * }
 * ```
 *
 * @property [description] A description of the target documentation. [CommonMark syntax](https://spec.commonmark.org/)
 * MAY be used for rich text representation.
 * @property [url] **REQUIRED**. The URL for the target documentation. This MUST be in the form of a URL.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#external-documentation-object)
 */
@Serializable
data class ExternalDocumentation(
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "url") val url: String
)
