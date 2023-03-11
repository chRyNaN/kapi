package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Allows configuration of the supported OAuth Flows.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * ## OAuthFlows Object Example
 *
 * ```json
 * {
 *   "type": "oauth2",
 *   "flows": {
 *     "implicit": {
 *       "authorizationUrl": "https://example.com/api/oauth/dialog",
 *       "scopes": {
 *         "write:pets": "modify pets in your account",
 *         "read:pets": "read your pets"
 *       }
 *     },
 *     "authorizationCode": {
 *       "authorizationUrl": "https://example.com/api/oauth/dialog",
 *       "tokenUrl": "https://example.com/api/oauth/token",
 *       "scopes": {
 *         "write:pets": "modify pets in your account",
 *         "read:pets": "read your pets"
 *       }
 *     }
 *   }
 * }
 * ```
 *
 * @property [implicit] Configuration for the OAuth Implicit flow.
 * @property [password] Configuration for the OAuth Resource Owner Password flow.
 * @property [clientCredentials] Configuration for the OAuth Client Credentials flow. Previously called `application`
 * in OpenAPI 2.0.
 * @property [authorizationCode] Configuration for the OAuth Authorization Code flow. Previously called `accessCode` in
 * OpenAPI 2.0.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#oauth-flows-object)
 */
@Serializable
data class OAuthFlows(
    @SerialName(value = "implicit") val implicit: OAuthFlow? = null,
    @SerialName(value = "password") val password: OAuthFlow? = null,
    @SerialName(value = "clientCredentials") val clientCredentials: OAuthFlow? = null,
    @SerialName(value = "authorizationCode") val authorizationCode: OAuthFlow? = null
)
