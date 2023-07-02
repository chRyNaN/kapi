@file:Suppress("MemberVisibilityCanBePrivate")

package com.chrynan.kapi.server.graphql.core.parser

import okio.*

class LineNumberReader(private val source: BufferedSource) : Source {

    var lineNumber = 0
        private set

    fun readLine(): String? {
        val line = source.readUtf8Line() ?: return null

        lineNumber++

        return line
    }

    override fun read(sink: Buffer, byteCount: Long): Long {
        val line = source.readUtf8Line() ?: return -1

        lineNumber++

        return line.utf8Size()
    }

    override fun timeout(): Timeout = source.timeout()

    override fun close() = source.close()
}
