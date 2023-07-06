package com.chrynan.kapi.server.graphql.core.parser

import okio.Buffer
import okio.Source
import okio.Timeout

/**
 * This [Source] will only emit a maximum number of characters from it. This is used to protect us from evil input.
 *
 * If a graphql system does not have some max HTTP input limit, then this will help protect the system.  This is a limit
 * of last resort. Ideally the http input should be limited, but if it's not, we have this.
 */
class SafeTokenReader(
    private val delegate: Source,
    private val maxCharacters: Long,
    private val whenMaxCharactersExceeded: (Long) -> Unit,
    private var count: Long = 0L
) : Source {

    override fun read(sink: Buffer, byteCount: Long): Long {
        val howMany = delegate.read(sink, byteCount)

        return checkHowMany(howMany, howMany)
    }

    override fun timeout(): Timeout =
        delegate.timeout()

    override fun close() = delegate.close()

    private fun checkHowMany(read: Long, howMany: Long): Long {
        if (read != -1L) {
            count += howMany
            if (count > maxCharacters) {
                whenMaxCharactersExceeded.invoke(maxCharacters)
            }
        }

        return read
    }
}
