package com.chrynan.kapi.server.graphql.core.parser

import com.chrynan.kapi.server.graphql.core.i18n.I18n
import com.chrynan.locale.core.Locale
import com.chrynan.locale.core.getDefault
import okio.Source

/**
 * This is the arguments that can be passed to a [Parser].
 *
 * @property [document] The [Source] representing the document to be parsed.
 * @property [parserOptions] The parsing options.
 * @property [i18N] The [I18n] instance used to produce error messages.
 */
class ParserEnvironment(
    val document: Source,
    val parserOptions: ParserOptions = ParserOptions(),
    val i18N: I18n = I18n(
        bundleType = I18n.BundleType.Parsing,
        locale = Locale.getDefault()
    )
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ParserEnvironment) return false

        if (document != other.document) return false
        if (parserOptions != other.parserOptions) return false

        return i18N == other.i18N
    }

    override fun hashCode(): Int {
        var result = document.hashCode()
        result = 31 * result + parserOptions.hashCode()
        result = 31 * result + i18N.hashCode()
        return result
    }

    override fun toString(): String =
        "ParserEnvironment(document=$document, parserOptions=$parserOptions, i18N=$i18N)"
}
