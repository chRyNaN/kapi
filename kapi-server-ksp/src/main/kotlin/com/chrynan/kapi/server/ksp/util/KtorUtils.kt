package com.chrynan.kapi.server.ksp.util

import io.ktor.http.*

internal fun ContentType.matches(pattern: String?): Boolean =
    pattern?.let { match(pattern) } ?: false
