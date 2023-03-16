package com.chrynan.kapi.server.processor.core.model

import com.chrynan.kapi.server.core.annotation.parameter.Header
import com.chrynan.kapi.server.core.annotation.ResponseHeaders
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Represents a [Header] annotation value when used in the [ResponseHeaders] annotation.
 *
 * @property [name] The header name.
 * @property [value] The header value.
 * @property [safeOnly] Determines whether to only add the header if it is considered safe. Defaults to `true`.
 * @property [onlyIfAbsent] Only adds the header value if it is not already present.
 *
 * @see [HeaderParameter] for a representation of the [Header] annotation when used on a parameter value of an API
 * function.
 */
@Serializable
data class ApiResponseHeader(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "value") val value: String,
    @SerialName(value = "safe_only") val safeOnly: Boolean = true,
    @SerialName(value = "only_if_absent") val onlyIfAbsent: Boolean = false
)
