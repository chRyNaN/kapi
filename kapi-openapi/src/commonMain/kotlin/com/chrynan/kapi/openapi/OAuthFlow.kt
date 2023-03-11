package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Configuration details for a supported OAuth Flow
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * ## OAuthFlow Object Example
 *
 * ```json
 * {
 *     "authorizationUrl": "https://example.com/api/oauth/dialog",
 *     "scopes": {
 *         "write:pets": "modify pets in your account",
 *         "read:pets": "read your pets"
 * }
 * ```
 *
 * @property [authorizationUrl] **REQUIRED**. The authorization URL to be used for this flow. This MUST be in the form
 * of a URL. The OAuth2 standard requires the use of TLS. Applies to `oauth2 ("implicit", "authorizationCode")`.
 * @property [tokenUrl] **REQUIRED**. The token URL to be used for this flow. This MUST be in the form of a URL.
 * The OAuth2 standard requires the use of TLS. Applies to
 * `oauth2 ("password", "clientCredentials", "authorizationCode")`.
 * @property [refreshUrl] The URL to be used for obtaining refresh tokens. This MUST be in the form of a URL. The
 * OAuth2 standard requires the use of TLS. Applies to `oauth2`.
 * @property [scopes] **REQUIRED**. The available scopes for the OAuth2 security scheme. A map between the scope name
 * and a short description for it. The map MAY be empty. Applies to `oauth2`.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#oauth-flow-object)
 */
@Serializable
data class OAuthFlow(
    @SerialName(value = "authorizationUrl") val authorizationUrl: String,
    @SerialName(value = "tokenUrl") val tokenUrl: String? = null,
    @SerialName(value = "refreshUrl") val refreshUrl: String,
    @SerialName(value = "scopes") val scopes: Map<String, String>
)
