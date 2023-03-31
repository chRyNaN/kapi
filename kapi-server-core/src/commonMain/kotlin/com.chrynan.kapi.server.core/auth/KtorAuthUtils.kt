@file:Suppress("unused")

package com.chrynan.kapi.server.core.auth

import com.auth0.jwt.interfaces.Payload
import com.chrynan.kapi.core.ApiError
import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import com.chrynan.kapi.server.core.util.respondError
import com.chrynan.kapi.server.core.util.tryOrNull
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock

/**
 * Requires the specified [Scope]s in order to access the nested routes. The [Scope]s are retrieved for an
 * [ApplicationCall], after any authentication has already succeeded, via the provided [onRetrieveScopes] function. The
 * application call's scopes are then compared to the provided [scopes] according to the provided [requirementPolicy]
 * to see if they are considered valid and the nested routes can be accessed. If the application call's scopes contain
 * all the required [scopes], then the nested routes may be accessed and the application call will continue through,
 * otherwise, the [onInvalid] function will be invoked.
 *
 * ## Example Usage
 *
 * ```kotlin
 * requireScopes(
 *     scopes = setOf(MyApiScopes.READ),
 *     onRetrieveScopes = { this.principal<JWTPrincipal>().getScopesSomehow() }
 * ) {
 *     get("/user/{id}) { ... } // Will only be invoked when this call has the appropriate `MyApiScopes.READ` scope.
 * }
 * ```
 */
@ExperimentalServerApi
fun Route.requireScopes(
    scopes: Set<Scope>,
    requirementPolicy: ScopeRequirementPolicy = ScopeRequirementPolicy.ALL,
    onRetrieveScopes: suspend ApplicationCall.() -> Set<Scope>,
    onInvalid: suspend ApplicationCall.() -> Unit = defaultInvalidHandler,
    build: Route.() -> Unit
) {
    require(scopes.isNotEmpty()) {
        "Cannot wrap route with `requiresScopes` function if no scopes are provided. Remove the `requiresScopes` function for routes that don't require scope authorization."
    }

    val route = createChild(TransparentRouteSelector())

    route.install(ScopeBasedAuthorizationPlugin) {
        this.scopes = scopes
        this.requirementPolicy = requirementPolicy
        this.onRetrieveScopes = onRetrieveScopes
        this.onInvalid = onInvalid
    }

    route.build()
}

/**
 * Requires the specified [Scope]s in order to access the nested routes. The [Scope]s are retrieved for an
 * [ApplicationCall], after any authentication has already succeeded, via the provided [onRetrieveScopes] function. The
 * application call's scopes are then compared to the provided [scopes] according to the provided [requirementPolicy]
 * to see if they are considered valid and the nested routes can be accessed. If the application call's scopes contain
 * all the required [scopes], then the nested routes may be accessed and the application call will continue through,
 * otherwise, the [onInvalid] function will be invoked.
 *
 * ## Example Usage
 *
 * ```kotlin
 * requireScopes(
 *     MyApiScopes.READ,
 *     onRetrieveScopes = { this.principal<JWTPrincipal>().getScopesSomehow() }
 * ) {
 *     get("/user/{id}) { ... } // Will only be invoked when this call has the appropriate `MyApiScopes.READ` scope.
 * }
 * ```
 */
@ExperimentalServerApi
fun Route.requireScopes(
    vararg scopes: Scope,
    requirementPolicy: ScopeRequirementPolicy = ScopeRequirementPolicy.ALL,
    onRetrieveScopes: suspend ApplicationCall.() -> Set<Scope>,
    onInvalid: suspend ApplicationCall.() -> Unit = defaultInvalidHandler,
    build: Route.() -> Unit
) = requireScopes(
    scopes = scopes.toSet(),
    requirementPolicy = requirementPolicy,
    onRetrieveScopes = onRetrieveScopes,
    onInvalid = onInvalid,
    build = build
)

/**
 * Requires the specified OAuth 2.0 Token [Scope]s in order to access the nested routes. This delegates to the
 * [requireScopes] function with [Scope] values obtained from a [JWTPrincipal] belonging to the provided authentication
 * [provider]. Note that [Scope]s are compared by their [Scope.value] property for this function. This should be fine
 * here since the scopes are retrieved via a particular authentication provider.
 *
 * ## Example Usage
 *
 * ```kotlin
 * requireScopes(
 *     provider = "myJwtAuthProvider",
 *     scopes = setOf(MyApiScopes.READ)
 * ) {
 *     get("/user/{id}) { ... } // Will only be invoked when this call has the appropriate `MyApiScopes.READ` scope.
 * }
 * ```
 */
@ExperimentalServerApi
fun Route.requireOauthScopes(
    provider: String?,
    scopes: Set<Scope>,
    requirementPolicy: ScopeRequirementPolicy = ScopeRequirementPolicy.ALL,
    onInvalid: suspend ApplicationCall.() -> Unit = defaultInvalidHandler,
    build: Route.() -> Unit
) = requireScopes(
    scopes = scopes,
    requirementPolicy = requirementPolicy,
    onRetrieveScopes = onRetrieveOauth2TokenScopes(provider),
    onInvalid = onInvalid,
    build = build
)

/**
 * Requires the specified OAuth 2.0 Token [Scope]s in order to access the nested routes. This delegates to the
 * [requireScopes] function with [Scope] values obtained from a [JWTPrincipal] belonging to the provided authentication
 * [provider]. Note that [Scope]s are compared by their [Scope.value] property for this function. This should be fine
 * here since the scopes are retrieved via a particular authentication provider.
 *
 * ## Example Usage
 *
 * ```kotlin
 * requireScopes(
 *     provider = "myJwtAuthProvider",
 *     scopes = setOf(MyApiScopes.READ)
 * ) {
 *     get("/user/{id}) { ... } // Will only be invoked when this call has the appropriate `MyApiScopes.READ` scope.
 * }
 * ```
 */
@ExperimentalServerApi
fun Route.requireOauthScopes(
    provider: String?,
    vararg scopes: Scope,
    requirementPolicy: ScopeRequirementPolicy = ScopeRequirementPolicy.ALL,
    onInvalid: suspend ApplicationCall.() -> Unit = defaultInvalidHandler,
    build: Route.() -> Unit
) = requireScopes(
    scopes = scopes,
    requirementPolicy = requirementPolicy,
    onRetrieveScopes = onRetrieveOauth2TokenScopes(provider),
    onInvalid = onInvalid,
    build = build
)

/**
 * Requires the specified OAuth 2.0 Token [Scope]s in order to access the nested routes. This delegates to the
 * [requireScopes] function with [Scope] values obtained from a [JWTPrincipal] belonging to the provided authentication
 * [provider]. Note that [Scope]s are compared by their [Scope.value] property for this function. This should be fine
 * here since the scopes are retrieved via a particular authentication provider.
 *
 * ## Example Usage
 *
 * ```kotlin
 * requireScopes(
 *     provider = "myJwtAuthProvider",
 *     scopes = setOf(MyApiScopes.READ)
 * ) {
 *     get("/user/{id}) { ... } // Will only be invoked when this call has the appropriate `MyApiScopes.READ` scope.
 * }
 * ```
 */
@ExperimentalServerApi
@JvmName("requireStringOauthScopes")
fun Route.requireOauthScopes(
    provider: String?,
    scopes: Set<String>,
    requirementPolicy: ScopeRequirementPolicy = ScopeRequirementPolicy.ALL,
    onInvalid: suspend ApplicationCall.() -> Unit = defaultInvalidHandler,
    build: Route.() -> Unit
) = requireScopes(
    scopes = scopes.map { StringValueScope(it) }.toSet(),
    requirementPolicy = requirementPolicy,
    onRetrieveScopes = onRetrieveOauth2TokenScopes(provider),
    onInvalid = onInvalid,
    build = build
)

/**
 * Requires the specified OAuth 2.0 Token [Scope]s in order to access the nested routes. This delegates to the
 * [requireScopes] function with [Scope] values obtained from a [JWTPrincipal] belonging to the provided authentication
 * [provider]. Note that [Scope]s are compared by their [Scope.value] property for this function. This should be fine
 * here since the scopes are retrieved via a particular authentication provider.
 *
 * ## Example Usage
 *
 * ```kotlin
 * requireScopes(
 *     provider = "myJwtAuthProvider",
 *     scopes = setOf(MyApiScopes.READ)
 * ) {
 *     get("/user/{id}) { ... } // Will only be invoked when this call has the appropriate `MyApiScopes.READ` scope.
 * }
 * ```
 */
@ExperimentalServerApi
@JvmName("requireStringOauthScopes")
fun Route.requireOauthScopes(
    provider: String?,
    vararg scopes: String,
    requirementPolicy: ScopeRequirementPolicy = ScopeRequirementPolicy.ALL,
    onInvalid: suspend ApplicationCall.() -> Unit = defaultInvalidHandler,
    build: Route.() -> Unit
) = requireScopes(
    scopes = scopes.map { StringValueScope(it) }.toSet(),
    requirementPolicy = requirementPolicy,
    onRetrieveScopes = onRetrieveOauth2TokenScopes(provider),
    onInvalid = onInvalid,
    build = build
)

@ExperimentalServerApi
internal val ScopeBasedAuthorizationPlugin =
    createRouteScopedPlugin("ScopeBasedAuthorizationPlugin", ::ScopeBasedAuthorizationConfiguration) {
        on(AuthenticationChecked) { call ->
            val scopes = pluginConfig.onRetrieveScopes.invoke(call)

            val isValid = when {
                pluginConfig.scopes.isEmpty() -> true
                pluginConfig.requirementPolicy == ScopeRequirementPolicy.ALL -> scopes.containsAll(pluginConfig.scopes)
                pluginConfig.requirementPolicy == ScopeRequirementPolicy.ANY -> scopes.any {
                    pluginConfig.scopes.contains(
                        it
                    )
                }

                else -> false
            }

            if (!isValid) {
                pluginConfig.onInvalid.invoke(call)
            }
        }
    }

@ExperimentalServerApi
private val defaultInvalidHandler: suspend ApplicationCall.() -> Unit = {
    this.respondError(
        error = ApiError(
            title = HttpStatusCode.Unauthorized.description,
            details = "You don`t have access to this resource.",
            status = HttpStatusCode.Unauthorized.value,
            timestamp = Clock.System.now()
        )
    )
}

@ExperimentalServerApi
private fun onRetrieveOauth2TokenScopes(provider: String?): suspend ApplicationCall.() -> Set<Scope> = {
    this.principal<JWTPrincipal>(provider)
        ?.payload
        ?.oauth2TokenScopeValuesOrNull()
        ?.map { StringValueScope(value = it) }
        ?.toSet() ?: emptySet()
}

/**
 * Attempts to retrieve the "scope" claim from a JWT token according to the
 * [OAuth 2.0 Token Exchange Specification](https://datatracker.ietf.org/doc/html/rfc8693#name-scope-scopes-claim).
 * That specification states that the scopes should be a [String] value of space-delimited scope values under the
 * "scope" claim in the JWT payload.
 */
@ExperimentalServerApi
private fun Payload.oauth2TokenScopeValuesOrNull(): Set<String>? =
    tryOrNull {
        this.getClaim("scope")?.asString()?.trim()?.split(' ')?.toSet()
    }

/**
 * Represents an authorization scope from a [Principal]. A [Scope] can be used to perform authorization after
 * authentication already succeeded by using the [requireScopes] function. It is common to have enum classes extend
 * this [Scope] interface to encapsulate all the possible scope parameter values.
 */
@ExperimentalServerApi
interface Scope {

    val value: String
}

/**
 * Determines whether all the provided scopes to the [requireScopes] function are required or if only one of them is
 * required. The [ANY] value indicates that only one of the provided scopes are required for the authorization to
 * succeed and access to be granted to the route. The [ALL] value indicates that all the provided scopes are required
 * for the authorization to succeed and access to be granted to the route.
 */
@ExperimentalServerApi
enum class ScopeRequirementPolicy {

    ANY,
    ALL
}

@ExperimentalServerApi
internal class ScopeBasedAuthorizationConfiguration {

    var scopes: Set<Scope> = emptySet()
    var requirementPolicy: ScopeRequirementPolicy = ScopeRequirementPolicy.ALL

    var onRetrieveScopes: suspend ApplicationCall.() -> Set<Scope> = { emptySet() }
    var onInvalid: suspend ApplicationCall.() -> Unit = defaultInvalidHandler
}

/**
 * A [RouteSelector] instance whose [RouteSelector.evaluate] function always returns the
 * [RouteSelectorEvaluation.Transparent] value which indicates that route evaluation succeeded for a
 * [RouteSelectorEvaluation.qualityTransparent] value. Useful for helper DSL methods that may wrap routes but should
 * not change priority of routing.
 */
@ExperimentalServerApi
private class TransparentRouteSelector : RouteSelector() {

    override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation =
        RouteSelectorEvaluation.Transparent
}

/**
 * An implementation of the [Scope] interface where if the [value] property is equal to another [Scope]s instance, then
 * it is considered the same value. **Note:** That this behavior may not be preferable in all cases, for instance, when
 * two "read" scopes belong to different authentication providers, they may be considered different, but this class
 * will consider them the same if their [value]s are equal.
 */
@ExperimentalServerApi
private class StringValueScope(override val value: String) : Scope {

    override fun hashCode(): Int = value.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Scope) return false

        return value == other.value
    }

    override fun toString(): String = "Scope(value=$value)"
}
