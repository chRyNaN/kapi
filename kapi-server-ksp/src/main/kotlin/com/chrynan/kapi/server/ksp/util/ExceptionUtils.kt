@file:Suppress("unused")

package com.chrynan.kapi.server.ksp.util

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSNode
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Represents an exception that was encountered during API validation when using the KSP plugin.
 */
internal class KspApiValidationException(
    message: String? = null,
    cause: Throwable? = null,
    val symbol: KSNode? = null
) : Exception(
    message,
    cause
)

/**
 * Logs an error by delegating to the [KSPLogger.throwError] function by wrapping the provided values in a
 * [KspApiValidationException].
 */
@Suppress("NOTHING_TO_INLINE") // We inline so the call-site is correct.
internal inline fun KSPLogger.throwError(message: String, symbol: KSNode? = null): Nothing =
    this.throwError(exception = KspApiValidationException(message = message, symbol = symbol))

/**
 * Logs an exception using the [KSPLogger.error] function with the values extracted from the [exception], and then
 * throws the exception.
 */
@Suppress("NOTHING_TO_INLINE") // We inline so the call-site is correct.
internal inline fun KSPLogger.throwError(exception: Exception): Nothing {
    val message = exception.message
    val symbol = (exception as? KspApiValidationException)?.symbol

    this.error(message = message ?: "", symbol = symbol)
    this.exception(exception)

    throw exception
}

/**
 * A [kotlin.error] function that takes a nullable [KSNode] [symbol] and throws a [KspApiValidationException] exception.
 */
@Suppress("NOTHING_TO_INLINE") // We inline so the call-site is correct.
internal inline fun error(message: Any, symbol: KSNode?): Nothing =
    throw KspApiValidationException(message = message.toString(), symbol = symbol)

/**
 * A [kotlin.check] function that takes a nullable [KSNode] [symbol] and throws a [KspApiValidationException] exception.
 */
@OptIn(ExperimentalContracts::class)
@Suppress("NOTHING_TO_INLINE") // We inline so the call-site is correct.
internal inline fun check(value: Boolean, symbol: KSNode?) {
    contract {
        returns() implies value
    }

    check(value = value, symbol = symbol) { "Check failed." }
}

/**
 * A [kotlin.check] function that takes a nullable [KSNode] [symbol] and throws a [KspApiValidationException] exception.
 */
@OptIn(ExperimentalContracts::class)
internal inline fun check(value: Boolean, symbol: KSNode?, lazyMessage: () -> Any) {
    contract {
        returns() implies value
    }

    if (!value) {
        val message = lazyMessage()

        throw KspApiValidationException(message = message.toString(), symbol = symbol)
    }
}

/**
 * A [kotlin.require] function that takes a nullable [KSNode] [symbol] and throws a [KspApiValidationException]
 * exception.
 */
@OptIn(ExperimentalContracts::class)
@Suppress("NOTHING_TO_INLINE") // We inline so the call-site is correct.
internal inline fun require(value: Boolean, symbol: KSNode?) {
    contract {
        returns() implies value
    }

    require(value = value, symbol = symbol) { "Failed requirement." }
}

/**
 * A [kotlin.require] function that takes a nullable [KSNode] [symbol] and throws a [KspApiValidationException]
 * exception.
 */
@OptIn(ExperimentalContracts::class)
internal inline fun require(value: Boolean, symbol: KSNode?, lazyMessage: () -> Any) {
    contract {
        returns() implies value
    }

    if (!value) {
        val message = lazyMessage()

        throw KspApiValidationException(message = message.toString(), symbol = symbol)
    }
}
