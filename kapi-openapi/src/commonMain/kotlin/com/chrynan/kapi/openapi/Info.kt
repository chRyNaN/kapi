package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * The object provides metadata about the API. The metadata MAY be used by the clients if needed, and MAY be presented
 * in editing or documentation generation tools for convenience.
 *
 * ## Info Object Example
 *
 * ```json
 * {
 *   "title": "Sample Pet Store App",
 *   "summary": "A pet store manager.",
 *   "description": "This is a sample server for a pet store.",
 *   "termsOfService": "https://example.com/terms/",
 *   "contact": {
 *     "name": "API Support",
 *     "url": "https://www.example.com/support",
 *     "email": "support@example.com"
 *   },
 *   "license": {
 *     "name": "Apache 2.0",
 *     "url": "https://www.apache.org/licenses/LICENSE-2.0.html"
 *   },
 *   "version": "1.0.1"
 * }
 * ```
 *
 * @property [title] **REQUIRED**. The title of the API.
 * @property [summary] A short summary of the API.
 * @property [description] A description of the API. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for
 * rich text representation.
 * @property [termsOfService] A URL to the Terms of Service for the API. This MUST be in the form of a URL.
 * @property [contact] The contact information for the exposed API.
 * @property [license] The license information for the exposed API.
 * @property [version] **REQUIRED**. The version of the OpenAPI document (which is distinct from the
 * [OpenAPI Specification version](https://spec.openapis.org/oas/v3.1.0#oasVersion) or the API implementation version).
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#info-object)
 */
@Serializable
data class Info(
    @SerialName(value = "title") val title: String,
    @SerialName(value = "summary") val summary: String? = null,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "termsOfService") val termsOfService: String? = null,
    @SerialName(value = "contact") val contact: Contact? = null,
    @SerialName(value = "license") val license: License? = null,
    @SerialName(value = "version") val version: String? = null
)
