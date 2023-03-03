package com.chrynan.kapi.server.ksp.util

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSNode

/**
 * Logs an error using the [KSPLogger.error] function with the provided parameters, and then throws an exception using
 * the [kotlin.error] function with the provided [message].
 */
internal fun KSPLogger.throwError(message: String, symbol: KSNode? = null): Nothing {
    this.error(message = message, symbol = symbol)

    kotlin.error(message)
}
