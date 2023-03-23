//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[OAuthFlows](index.md)

# OAuthFlows

[common]\
@Serializable

data class [OAuthFlows](index.md)(val implicit: [OAuthFlow](../-o-auth-flow/index.md)? = null, val password: [OAuthFlow](../-o-auth-flow/index.md)? = null, val clientCredentials: [OAuthFlow](../-o-auth-flow/index.md)? = null, val authorizationCode: [OAuthFlow](../-o-auth-flow/index.md)? = null)

Allows configuration of the supported OAuth Flows.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

##  OAuthFlows Object Example

```json
{
  "type": "oauth2",
  "flows": {
    "implicit": {
      "authorizationUrl": "https://example.com/api/oauth/dialog",
      "scopes": {
        "write:pets": "modify pets in your account",
        "read:pets": "read your pets"
      }
    },
    "authorizationCode": {
      "authorizationUrl": "https://example.com/api/oauth/dialog",
      "tokenUrl": "https://example.com/api/oauth/token",
      "scopes": {
        "write:pets": "modify pets in your account",
        "read:pets": "read your pets"
      }
    }
  }
}
```

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#oauth-flows-object) |

## Constructors

| | |
|---|---|
| [OAuthFlows](-o-auth-flows.md) | [common]<br>fun [OAuthFlows](-o-auth-flows.md)(implicit: [OAuthFlow](../-o-auth-flow/index.md)? = null, password: [OAuthFlow](../-o-auth-flow/index.md)? = null, clientCredentials: [OAuthFlow](../-o-auth-flow/index.md)? = null, authorizationCode: [OAuthFlow](../-o-auth-flow/index.md)? = null) |

## Properties

| Name | Summary |
|---|---|
| [authorizationCode](authorization-code.md) | [common]<br>val [authorizationCode](authorization-code.md): [OAuthFlow](../-o-auth-flow/index.md)? = null<br>Configuration for the OAuth Authorization Code flow. Previously called `accessCode` in OpenAPI 2.0. |
| [clientCredentials](client-credentials.md) | [common]<br>val [clientCredentials](client-credentials.md): [OAuthFlow](../-o-auth-flow/index.md)? = null<br>Configuration for the OAuth Client Credentials flow. Previously called `application` in OpenAPI 2.0. |
| [implicit](implicit.md) | [common]<br>val [implicit](implicit.md): [OAuthFlow](../-o-auth-flow/index.md)? = null<br>Configuration for the OAuth Implicit flow. |
| [password](password.md) | [common]<br>val [password](password.md): [OAuthFlow](../-o-auth-flow/index.md)? = null<br>Configuration for the OAuth Resource Owner Password flow. |
