@file:Suppress("unused")

package com.chrynan.kapi.server.core.annotation

import com.chrynan.kapi.server.core.annotation.Auth.RequirementType

/**
 * The component annotated with this class will require the provided [requirements] be met in order to access the API
 * component. The way in which the [requirements] are applied to the annotated component is dependent on the [requirementType]
 * value.
 *
 * The following list details the result of applying this annotation to different components:
 * * API function - that function will require the [requirements] be met in order for the API function to be accessed.
 * * API component - all the API component's API functions will require the [requirements] be met in order to access
 * those API functions.
 * * Annotation class - the annotation class will represent this annotation but with the specified values. When it is
 * applied to an API function or component, it will have the same effect as if this annotation with those values were
 * directly applied to that API function or component.
 *
 * **Note:** That when multiple [Auth]s apply to an API component or function, they are concatenated with a logical AND
 * operation. This means that all the specified [SecurityRequirement]s in each [Auth] definition must be met to access
 * the API component or function.
 *
 * **Example usage:**
 *
 * ```kotlin
 * @Auth(SecurityRequirement(name = "myJwtApiToken", scopes = arrayOf("read")))
 * @Target(AnnotationTarget.FUNCTION)
 * annotation class RequireReadAccess
 *
 * @Api(
 *     securitySchemes = arrayOf(SecurityScheme(name = "myJwtApiToken", ...)), ...)
 * interface MyApi {
 *
 *     @RequireReadAccess // Equivalent to using `@Auth(SecurityRequirement(name = "myJwtApiToken", scopes = arrayOf("read")))` here
 *     @GET("/user/{id}")
 *     suspend fun getUser(@Path id: String): User
 * }
 * ```
 *
 * @property [requirements] The [SecurityRequirement]s that need to be met in order to access the annotated API
 * component.
 * @property [requirementType] Defines how the [requirements] are applied. If the value is [RequirementType.ALL], then
 * it is equivalent to a logical AND, meaning that all the requirements must be met in order to access the component.
 * If the value is [RequirementType.ANY], then it is equivalent to a logical OR, meaning only one of the requirements
 * must be met in order to access the component.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@MustBeDocumented
annotation class Auth(
    vararg val requirements: SecurityRequirement,
    val requirementType: RequirementType = RequirementType.ALL
) {

    /**
     * Defines how multiple [SecurityRequirement]s are applied to an API component or function. A value of
     * [RequirementType.ALL] is equivalent to a logical AND, meaning that all the requirements must be met in order to
     * access the component. A value of [RequirementType.ANY] is equivalent to a logical OR, meaning that only one of
     * the requirements must be met in order to access the component.
     */
    enum class RequirementType {

        ANY,
        ALL
    }
}

/**
 * An API component containing this [SecurityRequirement] will require the security scheme identified by the [name]
 * property. There must be a corresponding [SecurityScheme] set up in the [Api] annotation, otherwise a compile error
 * will be thrown.
 *
 * @property [name] The name of the [SecurityScheme] that is required to access the annotated component. This value
 * must not be blank, and it must have a corresponding [SecurityScheme] with the same name within the
 * [Api.securitySchemes].
 * @property [scopes] The scopes that are required to access the annotated component. Usage of this property depends on
 * the particular [SecurityScheme] type.
 * @property [scopeRequirementType] Defines how the [scopes] are applied. If the value is [RequirementType.ALL], then
 * it is equivalent to a logical AND, meaning that all the scopes must be present in order to access the component. If
 * the value is [RequirementType.ANY], then it is equivalent to a logical OR, meaning only one of the scopes must be
 * present in order to access the component.
 */
@Target()
@MustBeDocumented
annotation class SecurityRequirement(
    val name: String,
    val scopes: Array<String> = [],
    val scopeRequirementType: RequirementType = RequirementType.ALL
)

/**
 * Defines an OpenAPI security scheme supported by an [Api] component. The supported [SecurityScheme]s for an API must
 * be provided to the [Api.securitySchemes] property of the [Api] annotation to be registered and used within that API
 * component.
 *
 * @property [name] The name of this [SecurityScheme]. This value should correspond to the [SecurityRequirement.name]
 * of the [SecurityRequirement] annotation when that is used to reference this [SecurityScheme]. This value must not be
 * blank.
 * @property [type] The type of security scheme this represents. This corresponds to the Open API Specification
 * supported type values.
 * @property [description] An optional description of this security scheme.
 * @property [flows] [OAuthFlow]s supported by this security scheme. Applies to [SecurityScheme.Type.OAUTH2].
 * @property [keyLocation] The location of where an API key is located within an API function request. Applies to
 * [SecurityScheme.Type.API_KEY].
 * @property [keyName] The name of the key used within an API function request. Applies to
 * [SecurityScheme.Type.API_KEY].
 * @property [scheme] The name of the HTTP Authorization scheme to be used in the Authorization header as defined in
 * [RFC7235](https://spec.openapis.org/oas/v3.1.0#bib-RFC7235). Applies to [SecurityScheme.Type.HTTP].
 * @property [bearerFormat] A hint to the client to identify how the bearer token is formatted. Bearer tokens are
 * usually generated by an authorization server, so this information is primarily for documentation purposes. Applies
 * to `http ("bearer")`.
 * @property [openIdConnectUrl] OpenId Connect URL to discover OAuth2 configuration values. This MUST be in the form of
 * a URL. The OpenID Connect standard requires the use of TLS. Applies to [SecurityScheme.Type.OPEN_ID_CONNECT].
 */
@Target()
@MustBeDocumented
annotation class SecurityScheme(
    val name: String,
    val type: Type,
    val description: String = "",
    val flows: Array<OAuthFlow> = [],
    val keyLocation: KeyLocation = KeyLocation.UNDEFINED,
    val keyName: String = "",
    val scheme: String = "",
    val bearerFormat: String = "",
    val openIdConnectUrl: String = ""
) {

    /**
     * The supported [SecurityScheme] types. These typically correspond to the supported security schemes in the Open
     * API specification.
     */
    enum class Type {

        API_KEY,
        HTTP,
        MUTUAL_TLS,
        OAUTH2,
        OPEN_ID_CONNECT
    }
}

val SecurityScheme.Type.openApiValue: String
    get() = when (this) {
        SecurityScheme.Type.API_KEY -> "apiKey"
        SecurityScheme.Type.HTTP -> "http"
        SecurityScheme.Type.MUTUAL_TLS -> "mutualTLS"
        SecurityScheme.Type.OAUTH2 -> "oauth2"
        SecurityScheme.Type.OPEN_ID_CONNECT -> "openIdConnect"
    }

enum class KeyLocation {

    HEADER,
    QUERY,
    COOKIE,
    UNDEFINED
}

val KeyLocation.openApiValue: String?
    get() = when (this) {
        KeyLocation.HEADER -> "header"
        KeyLocation.QUERY -> "query"
        KeyLocation.COOKIE -> "cookie"
        else -> null
    }

@Target()
@MustBeDocumented
annotation class OAuthFlow(
    val type: Type,
    val authorizationUrl: String,
    val tokenUrl: String = "",
    val refreshUrl: String = "",
    val scopes: Array<OAuthScope> = []
) {

    enum class Type {

        IMPLICIT,
        PASSWORD,
        CLIENT_CREDENTIALS,
        AUTHORIZATION_CODE
    }
}

@Target()
@MustBeDocumented
annotation class OAuthScope(
    val name: String,
    val description: String = ""
)
