@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.parser

import com.chrynan.kapi.server.graphql.core.util.bufferedAsUtf8
import okio.*

class MultiSourceReader(
    private val sourceParts: List<SourcePart>,
    internal val data: StringBuilder = StringBuilder(),
    private var currentIndex: Int = 0,
    private var overallLineNumber: Int = 0,
    private val trackData: Boolean = false
) : Source {

    /**
     * This returns the source name and line number given an overall line number
     *
     * This is zeroes based like [java.io.LineNumberReader.getLineNumber]
     *
     * @param overallLineNumber the over all line number
     *
     * @return the source name and relative line number to that source
     */
    fun getSourceAndLineFromOverallLine(overallLineNumber: Int): SourceAndLine {
        val sourceAndLine = SourceAndLine()
        var sourceName: String? = null

        if (sourceParts.isEmpty()) {
            return sourceAndLine
        }

        if (overallLineNumber == 0) {
            return SourceAndLine(
                sourceName = sourceParts[0].sourceName,
                line = 0
            )
        }

        val currentPart: SourcePart = if (currentIndex >= sourceParts.size) {
            sourceParts[sourceParts.size - 1]
        } else {
            sourceParts[currentIndex]
        }

        var page = 0
        var previousPage: Int

        for (sourcePart in sourceParts) {
            sourceName = sourcePart.sourceName

            if (sourcePart === currentPart) {
                // we cant go any further
                val partLineNumber = currentPart.lineReader.lineNumber

                previousPage = page
                page += partLineNumber

                return SourceAndLine(
                    sourceName = sourcePart.sourceName,
                    line = if (page > overallLineNumber) {
                        overallLineNumber - previousPage
                    } else {
                        page
                    }
                )
            } else {
                previousPage = page

                val partLineNumber = sourcePart.lineReader.lineNumber

                page += partLineNumber

                if (page > overallLineNumber) {
                    return SourceAndLine(
                        sourceName = sourcePart.sourceName,
                        line = overallLineNumber - previousPage
                    )
                }
            }
        }

        return SourceAndLine(
            sourceName = sourceName,
            line = overallLineNumber - page
        )
    }

    /**
     * @return the line number of the current source.  This is zeroes based like [java.io.LineNumberReader.getLineNumber]
     */
    fun getLineNumber(): Int {
        synchronized(this) {
            if (sourceParts.isEmpty()) {
                return 0
            }

            return if (currentIndex >= sourceParts.size) {
                sourceParts[sourceParts.size - 1].lineReader.lineNumber
            } else sourceParts[currentIndex].lineReader.lineNumber
        }
    }

    /**
     * @return The name of the current source
     */
    fun getSourceName(): String? {
        synchronized(this) {
            if (sourceParts.isEmpty()) {
                return null
            }

            return if (currentIndex >= sourceParts.size) {
                sourceParts[sourceParts.size - 1].sourceName
            } else sourceParts[currentIndex].sourceName
        }
    }

    /**
     * @return the overall line number of the all the sources.  This is zeroes based like [java.io.LineNumberReader.getLineNumber]
     */
    fun getOverallLineNumber(): Int =
        overallLineNumber

    fun getData(): List<String> {
        val reader = LineNumberReader(source = data.toString().bufferedAsUtf8())
        val lines: MutableList<String> = ArrayList()

        while (true) {
            val line = reader.readLine() ?: return lines

            lines.add(line)
        }
    }

    override fun read(sink: Buffer, byteCount: Long): Long {
        while (true) {
            synchronized(this) {
                if (currentIndex >= sourceParts.size) {
                    return -1
                }

                val sourcePart = sourceParts[currentIndex]
                val read = sourcePart.lineReader.read(sink, byteCount)

                overallLineNumber = calcLineNumber()

                if (read == -1L) {
                    currentIndex++
                } else {
                    trackData(sink, byteCount)
                    return read
                }
            }
        }
    }

    override fun timeout(): Timeout =
        sourceParts.map { it.lineReader.timeout() }
            .minBy { it.timeoutNanos() }

    override fun close() {
        for (sourcePart in sourceParts) {
            if (!sourcePart.closed) {
                sourcePart.lineReader.close()
                sourcePart.closed = true
            }
        }
    }

    private fun calcLineNumber(): Int {
        var linenumber = 0

        for (sourcePart in sourceParts) {
            linenumber += sourcePart.lineReader.lineNumber
        }

        return linenumber
    }

    private fun trackData(sink: Buffer, byteCount: Long) {
        if (trackData) {
            data.append(sink.readUtf8(byteCount))
        }
    }

    class SourceAndLine(
        val sourceName: String? = null,
        val line: Int = 0
    ) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is SourceAndLine) return false

            if (sourceName != other.sourceName) return false

            return line == other.line
        }

        override fun hashCode(): Int {
            var result = sourceName?.hashCode() ?: 0
            result = 31 * result + line
            return result
        }

        override fun toString(): String =
            "SourceAndLine(sourceName=$sourceName, line=$line)"
    }

    class SourcePart(
        var sourceName: String? = null,
        var lineReader: LineNumberReader,
        var closed: Boolean = false
    ) {

        constructor(
            sourceName: String? = null,
            source: Source,
            closed: Boolean = false
        ) : this(
            sourceName = sourceName,
            lineReader = LineNumberReader(source = source.buffer()),
            closed = closed
        )

        constructor(
            sourceName: String? = null,
            source: String,
            closed: Boolean = false
        ) : this(
            sourceName = sourceName,
            lineReader = LineNumberReader(source = source.bufferedAsUtf8()),
            closed = closed
        )

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is SourcePart) return false

            if (sourceName != other.sourceName) return false
            if (lineReader != other.lineReader) return false

            return closed == other.closed
        }

        override fun hashCode(): Int {
            var result = sourceName?.hashCode() ?: 0
            result = 31 * result + lineReader.hashCode()
            result = 31 * result + closed.hashCode()
            return result
        }

        override fun toString(): String =
            "SourcePart(sourceName=$sourceName, lineReader=$lineReader, closed=$closed)"
    }
}
