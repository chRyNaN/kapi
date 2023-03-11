package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * An object representing a Server.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * ## Server Object Example
 *
 * ```json
 * {
 *   "url": "https://development.gigantic-server.com/v1",
 *   "description": "Development server"
 * }
 * ```
 *
 * @property [url] **REQUIRED**. A URL to the target host. This URL supports Server Variables and MAY be relative, to
 * indicate that the host location is relative to the location where the OpenAPI document is being served. Variable
 * substitutions will be made when a variable is named in {brackets}.
 * @property [description] An optional string describing the host designated by the URL.
 * [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation.
 * @property [variables] A map between a variable name and its value. The value is used for substitution in the
 * server’s URL template.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#server-object)
 */
@Serializable
data class Server(
    @SerialName(value = "url") val url: String,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "variables") val variables: Map<String, ServerVariable>? = null
)
