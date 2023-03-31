package com.chrynan.kapi.server.core.annotation

/**
 * This annotation marks a component as experimental for server-side development and likely to change. Use a component
 * marked with this annotation cautiously.
 */
@RequiresOptIn(
    message = "This is an experimental server API. It is possible that it changes in the future. Use with caution.",
    level = RequiresOptIn.Level.WARNING
)
annotation class ExperimentalServerApi
