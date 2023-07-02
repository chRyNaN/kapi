@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.parser

import com.chrynan.kapi.server.graphql.core.language.SourceLocation
import graphql.parser.MultiSourceReader
import org.antlr.v4.kotlinruntime.Token
import org.antlr.v4.kotlinruntime.tree.TerminalNode

internal object AntlrHelper {

    fun createSourceLocation(
        multiSourceReader: MultiSourceReader,
        antrlLine: Int,
        charPositionInLine: Int
    ): SourceLocation {
        // multi source reader lines are 0 based while Antler lines are 1's based
        //
        // Antler columns ironically are 0 based - go figure!
        //
        val tokenLine = antrlLine - 1
        val sourceAndLine = multiSourceReader.getSourceAndLineFromOverallLine(tokenLine)
        //
        // graphql spec says line numbers and columns start at 1
        val line = sourceAndLine.line + 1
        val column = charPositionInLine + 1

        return SourceLocation(line, column, sourceAndLine.sourceName)
    }

    fun createSourceLocation(multiSourceReader: MultiSourceReader, token: Token): SourceLocation {
        return createSourceLocation(multiSourceReader, token.line, token.charPositionInLine)
    }

    fun createSourceLocation(multiSourceReader: MultiSourceReader, terminalNode: TerminalNode): SourceLocation {
        return createSourceLocation(
            multiSourceReader,
            terminalNode.symbol?.line ?: 0,
            terminalNode.symbol?.charPositionInLine ?: 0
        )
    }

    /* grabs 3 lines before and after the syntax error */
    fun createPreview(multiSourceReader: MultiSourceReader, antrlLine: Int): String {
        val line = antrlLine - 1
        val sb = StringBuilder()
        val startLine = line - 3
        val endLine = line + 3
        val lines = multiSourceReader.data

        for (i in lines.indices) {
            if (i in startLine..endLine) {
                sb.append(lines[i]).append('\n')
            }
        }

        return sb.toString()
    }
}
