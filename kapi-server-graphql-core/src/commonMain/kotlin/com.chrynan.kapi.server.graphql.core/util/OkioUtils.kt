package com.chrynan.kapi.server.graphql.core.util

import okio.Buffer
import okio.BufferedSource

internal fun String.bufferedAsUtf8(): BufferedSource {
    val buffer = Buffer()

    buffer.writeUtf8(this)

    return buffer
}
