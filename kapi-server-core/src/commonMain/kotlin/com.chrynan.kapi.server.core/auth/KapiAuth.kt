package com.chrynan.kapi.server.core.auth

import com.chrynan.kapi.core.Kapi

/**
 * Obtains a [KapiAuth] which can be used for generation of auth related components for use with the kapi library.
 */
@Suppress("UnusedReceiverParameter")
val Kapi.auth: KapiAuth
    get() = kapiAuthSingleton

private val kapiAuthSingleton = KapiAuth()

/**
 * A convenience class for scoping authentication or authorization components related to the kapi library. This can
 * help for easily accessing generated auth components. To obtain an instance of this class, use the [Kapi.auth]
 * property.
 */
class KapiAuth internal constructor()
