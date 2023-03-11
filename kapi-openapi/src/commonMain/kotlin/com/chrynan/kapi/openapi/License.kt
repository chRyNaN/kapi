package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * License information for the exposed API.
 *
 * ## License Object Example
 *
 * ```json
 * {
 *   "name": "Apache 2.0",
 *   "identifier": "Apache-2.0"
 * }
 * ```
 *
 * @property [name] **REQUIRED**. The license name used for the API.
 * @property [identifier] An [SPDX](https://spdx.dev/spdx-specification-21-web-version/#h.jxpfx0ykyb60) license
 * expression for the API. The identifier field is mutually exclusive of the url field.
 * @property [url] A URL to the license used for the API. This MUST be in the form of a URL. The url field is mutually
 * exclusive of the identifier field.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#license-object)
 */
@Serializable
data class License(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "identifier") val identifier: String? = null,
    @SerialName(value = "url") val url: String? = null
)
