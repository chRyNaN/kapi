package com.chrynan.kapi.server.graphql.core.util

import com.chrynan.kapi.server.graphql.core.i18n.I18n
import com.chrynan.kapi.server.graphql.core.language.SourceLocation
import com.chrynan.kapi.server.graphql.core.parser.exception.InvalidUnicodeSyntaxException
import com.chrynan.kapi.server.graphql.core.util.Assert.assertShouldNeverHappen
import okio.IOException

/**
 * Contains Unicode helpers for parsing StringValue types in the grammar
 */
object UnicodeUtil {

    private const val MAX_UNICODE_CODE_POINT = 0x10FFFF
    private const val LEADING_SURROGATE_LOWER_BOUND = 0xD800
    private const val LEADING_SURROGATE_UPPER_BOUND = 0xDBFF
    private const val TRAILING_SURROGATE_LOWER_BOUND = 0xDC00
    private const val TRAILING_SURROGATE_UPPER_BOUND = 0xDFFF

    fun parseAndWriteUnicode(
        i18n: I18n,
        writer: StringBuilder,
        string: String,
        i: Int,
        sourceLocation: SourceLocation
    ): Int {
        // Unicode code points can either be:
        //  1. Unbraced: four hex characters in the form \\u597D, or
        //  2. Braced: any number of hex characters surrounded by braces in the form \\u{1F37A}

        // Extract the code point hex digits. Index i points to 'u'
        var i = i
        val startIndex = if (isBracedEscape(string, i)) i + 2 else i + 1
        val endIndexExclusive = getEndIndexExclusive(i18n, string, i, sourceLocation)
        // Index for parser to continue at, the last character of the escaped unicode character. Either } or hex digit
        var continueIndex = if (isBracedEscape(string, i)) endIndexExclusive else endIndexExclusive - 1
        val hexStr = string.substring(startIndex, endIndexExclusive)
        val codePoint = hexStr.toInt(16)
        if (isTrailingSurrogateValue(codePoint)) {
            throw InvalidUnicodeSyntaxException(
                i18n,
                "InvalidUnicode.trailingLeadingSurrogate",
                sourceLocation,
                offendingToken(string, i, continueIndex)
            )
        } else if (isLeadingSurrogateValue(codePoint)) {
            if (!isEscapedUnicode(string, continueIndex + 1)) {
                throw InvalidUnicodeSyntaxException(
                    i18n,
                    "InvalidUnicode.leadingTrailingSurrogate",
                    sourceLocation,
                    offendingToken(string, i, continueIndex)
                )
            }

            // Shift parser ahead to 'u' in second escaped Unicode character
            i = continueIndex + 2
            val trailingStartIndex = if (isBracedEscape(string, i)) i + 2 else i + 1
            val trailingEndIndexExclusive = getEndIndexExclusive(i18n, string, i, sourceLocation)
            val trailingHexStr = string.substring(trailingStartIndex, trailingEndIndexExclusive)
            val trailingCodePoint = trailingHexStr.toInt(16)
            continueIndex = if (isBracedEscape(string, i)) trailingEndIndexExclusive else trailingEndIndexExclusive - 1

            if (isTrailingSurrogateValue(trailingCodePoint)) {
                writeCodePoint(writer, codePoint)
                writeCodePoint(writer, trailingCodePoint)
                return continueIndex
            }

            throw InvalidUnicodeSyntaxException(
                i18n,
                "InvalidUnicode.leadingTrailingSurrogate",
                sourceLocation,
                offendingToken(string, i, continueIndex)
            )
        } else if (isValidUnicodeCodePoint(codePoint)) {
            writeCodePoint(writer, codePoint)

            return continueIndex
        }

        throw InvalidUnicodeSyntaxException(
            i18n,
            "InvalidUnicode.invalidCodePoint",
            sourceLocation,
            offendingToken(string, i, continueIndex)
        )
    }

    private fun offendingToken(string: String, i: Int, continueIndex: Int): String =
        string.substring(i - 1, continueIndex + 1)

    private fun getEndIndexExclusive(i18n: I18n, string: String, i: Int, sourceLocation: SourceLocation): Int {
        // Unbraced case, with exactly 4 hex digits
        if (string.length > i + 5 && !isBracedEscape(string, i)) {
            return i + 5
        }

        // Braced case, with any number of hex digits
        var endIndexExclusive = i + 2
        do {
            if (endIndexExclusive + 1 >= string.length) {
                throw InvalidUnicodeSyntaxException(
                    i18n,
                    "InvalidUnicode.incorrectEscape",
                    sourceLocation,
                    string.substring(i - 1, endIndexExclusive)
                )
            }
        } while (string[++endIndexExclusive] != '}')

        return endIndexExclusive
    }

    private fun isValidUnicodeCodePoint(value: Int): Boolean =
        value <= MAX_UNICODE_CODE_POINT

    private fun isEscapedUnicode(string: String, index: Int): Boolean =
        if (index + 1 >= string.length) {
            false
        } else string[index] == '\\' && string[index + 1] == 'u'

    private fun isLeadingSurrogateValue(value: Int): Boolean =
        value in LEADING_SURROGATE_LOWER_BOUND..LEADING_SURROGATE_UPPER_BOUND

    private fun isTrailingSurrogateValue(value: Int): Boolean =
        value in TRAILING_SURROGATE_LOWER_BOUND..TRAILING_SURROGATE_UPPER_BOUND

    private fun writeCodePoint(writer: StringBuilder, codepoint: Int) {
        val chars = Character.toChars(codepoint)

        try {
            writer.append(chars)
        } catch (e: IOException) {
            assertShouldNeverHappen()
        }
    }

    private fun isBracedEscape(string: String, i: Int): Boolean =
        string[i + 1] == '{'
}
