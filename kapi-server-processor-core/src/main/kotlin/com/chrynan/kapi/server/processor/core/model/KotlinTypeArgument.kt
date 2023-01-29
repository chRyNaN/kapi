package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Represents a Kotlin type argument. Note that this represents a type argument and not a type parameter. A
 * [KotlinTypeArgument] is the value provided to a generic function or component use-site call, whereas a type
 * parameter is the variable used at the definition of the generic component. For example, a [KotlinTypeArgument] could
 * be the following:
 *
 * ```kotlin
 * // The `User` as the generic type argument of the `Response` return type of the following function call is a
 * // "type argument":
 * suspend fun getUser(): Response<User>
 * ```
 *
 * @property [variance] The [KotlinGenericVariance] of this type argument.
 * @property [type] The [KotlinTypeUsage] representing the Kotlin type provided as this argument.
 */
@Serializable
data class KotlinTypeArgument(
    @SerialName(value = "variance") val variance: KotlinGenericVariance,
    @SerialName(value = "type") val type: KotlinTypeUsage
)
