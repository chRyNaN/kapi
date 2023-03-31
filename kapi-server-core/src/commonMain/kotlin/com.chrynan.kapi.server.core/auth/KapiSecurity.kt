package com.chrynan.kapi.server.core.auth

import com.chrynan.kapi.core.Kapi
import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi

/**
 * Obtains a [KapiSecurity] which can be used for generation of auth related components for use with the kapi library.
 */
@Suppress("UnusedReceiverParameter")
@ExperimentalServerApi
val Kapi.security: KapiSecurity
    get() = kapiSecuritySingleton

@ExperimentalServerApi
private val kapiSecuritySingleton = KapiSecurity()

/**
 * A convenience class for scoping authentication or authorization components related to the kapi library. This can
 * help for easily accessing generated auth components. To obtain an instance of this class, use the [Kapi.security]
 * property.
 */
@ExperimentalServerApi
class KapiSecurity internal constructor()
