package com.chrynan.kapi.server.core.util

/**
 * Invokes the provided [block] returning the result or `null` if an exception was thrown.
 */
internal fun <R> tryOrNull(block: () -> R): R? =
    try {
        block()
    } catch (_: Throwable) {
        null
    }
