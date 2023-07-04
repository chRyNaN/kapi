package com.chrynan.kapi.server.graphql.core.parser

import com.chrynan.kapi.server.graphql.core.i18n.I18n
import com.chrynan.kapi.server.graphql.core.language.SourceLocation
import com.chrynan.kapi.server.graphql.core.util.Assert
import com.chrynan.kapi.server.graphql.core.util.UnicodeUtil

/**
 * Contains parsing code for the StringValue types in the grammar
 */
@Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")
object StringValueParsing {

    private const val ESCAPED_TRIPLE_QUOTES = "\\\\\"\"\"" // ahh Java + Regex
    private const val THREE_QUOTES = "\"\"\""

    fun parseTripleQuotedString(strText: String): String {
        val end = strText.length - 3
        var s = strText.substring(3, end)
        s = s.replace(ESCAPED_TRIPLE_QUOTES.toRegex(), THREE_QUOTES)
        return removeIndentation(s)
    }

    /*
       See https://github.com/facebook/graphql/pull/327/files#diff-fe406b08746616e2f5f00909488cce66R758
     */
    fun removeIndentation(rawValue: String): String {
        val lines = rawValue.split("\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var commonIndent: Int? = null

        for (i in lines.indices) {
            if (i == 0) {
                continue
            }
            val line = lines[i]
            val length = line.length
            val indent = leadingWhitespace(line)
            if (indent < length) {
                if (commonIndent == null || indent < commonIndent) {
                    commonIndent = indent
                }
            }
        }

        val lineList = lines.toMutableList()

        if (commonIndent != null) {
            for (i in lineList.indices) {
                var line = lineList[i]
                if (i == 0) {
                    continue
                }
                if (line.length > commonIndent) {
                    line = line.substring(commonIndent)
                    lineList[i] = line
                }
            }
        }

        while (lineList.isNotEmpty()) {
            val line = lineList[0]
            if (containsOnlyWhiteSpace(line)) {
                lineList.removeAt(0)
            } else {
                break
            }
        }

        while (lineList.isNotEmpty()) {
            val endIndex = lineList.size - 1
            val line = lineList[endIndex]
            if (containsOnlyWhiteSpace(line)) {
                lineList.removeAt(endIndex)
            } else {
                break
            }
        }

        val formatted = StringBuilder()

        for (i in lineList.indices) {
            val line = lineList[i]
            if (i == 0) {
                formatted.append(line)
            } else {
                formatted.append("\n")
                formatted.append(line)
            }
        }

        return formatted.toString()
    }

    private fun leadingWhitespace(str: String): Int {
        var count = 0

        for (element in str) {
            if (element != ' ' && element != '\t') {
                break
            }
            count++
        }

        return count
    }

    private fun containsOnlyWhiteSpace(str: String): Boolean {
        // according to graphql spec and graphql-js - this is the definition
        return leadingWhitespace(str) == str.length
    }

    fun parseSingleQuotedString(i18n: I18n, string: String, sourceLocation: SourceLocation): String {
        val writer = StringBuilder(string.length - 2)
        val end = string.length - 1
        var i = 1

        while(i < end) {
            val c = string[i]

            if (c != '\\') {
                writer.append(c.code)
                i++
                continue
            }

            val escaped = string[i + 1]
            i += 1

            when (escaped) {
                '"' -> {
                    writer.append('"'.code)
                    i++
                    continue
                }

                '/' -> {
                    writer.append('/'.code)
                    i++
                    continue
                }

                '\\' -> {
                    writer.append('\\'.code)
                    i++
                    continue
                }

                'b' -> {
                    writer.append('\b'.code)
                    i++
                    continue
                }

                'f' -> {
                    writer.append('f'.code)
                    i++
                    continue
                }

                'n' -> {
                    writer.append('\n'.code)
                    i++
                    continue
                }

                'r' -> {
                    writer.append('\r'.code)
                    i++
                    continue
                }

                't' -> {
                    writer.append('\t'.code)
                    i++
                    continue
                }

                'u' -> {
                    i = UnicodeUtil.parseAndWriteUnicode(i18n, writer, string, i, sourceLocation)
                    i++
                    continue
                }

                else -> Assert.assertShouldNeverHappen()
            }
        }

        return writer.toString()
    }
}
