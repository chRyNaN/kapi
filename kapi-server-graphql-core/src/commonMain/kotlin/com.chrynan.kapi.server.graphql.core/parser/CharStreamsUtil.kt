package com.chrynan.kapi.server.graphql.core.parser

import okio.Source
import okio.buffer
import org.antlr.v4.kotlinruntime.CharStream
import org.antlr.v4.kotlinruntime.CharStreams
import org.antlr.v4.kotlinruntime.IntStream
import org.antlr.v4.kotlinruntime.StringCharStream

fun CharStreams.fromSource(
    source: Source,
    sourceName: String = IntStream.UNKNOWN_SOURCE_NAME
): CharStream {
    source.use {
        return StringCharStream(
            source = it.buffer().readUtf8(),
            sourceName = sourceName
        )
    }
}
