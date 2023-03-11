package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Contact information for the exposed API.
 *
 * ## Contact Object Example
 *
 * ```json
 * {
 *   "name": "API Support",
 *   "url": "https://www.example.com/support",
 *   "email": "support@example.com"
 * }
 * ```
 *
 * @property [name] The identifying name of the contact person/organization.
 * @property [url] The URL pointing to the contact information. This MUST be in the form of a URL.
 * @property [email] The email address of the contact person/organization. This MUST be in the form of an email address.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#contact-object)
 */
@Serializable
data class Contact(
    @SerialName(value = "name") val name: String? = null,
    @SerialName(value = "url") val url: String? = null,
    @SerialName(value = "email") val email: String? = null
)
