package com.chrynan.kapi.server.graphql.core.parser

import com.chrynan.kapi.server.graphql.core.language.SourceLocation
import com.chrynan.kapi.server.graphql.core.parser.AntlrHelper.createPreview
import com.chrynan.kapi.server.graphql.core.parser.exception.InvalidSyntaxException
import com.chrynan.kapi.server.graphql.core.parser.exception.MoreTokensSyntaxException
import graphql.com.google.common.collect.ImmutableList
import org.antlr.v4.kotlinruntime.*
import org.antlr.v4.kotlinruntime.Parser
import org.antlr.v4.kotlinruntime.misc.ParseCancellationException

internal class ExtendedBailStrategy(
    private val multiSourceReader: MultiSourceReader,
    private val environment: ParserEnvironment,
    private val delegate: BailErrorStrategy = BailErrorStrategy()
) : DefaultErrorStrategy() {

    override fun recover(recognizer: Parser, e: RecognitionException) {
        try {
            delegate.recover(recognizer, e)
        } catch (parseException: ParseCancellationException) {
            throw makeException(recognizer, e)
        }
    }

    @Throws(RecognitionException::class)
    override fun recoverInline(recognizer: Parser): Token =
        try {
            delegate.recoverInline(recognizer)
        } catch (parseException: ParseCancellationException) {
            throw makeException(recognizer, null)
        }

    fun makeMoreTokensException(token: Token): InvalidSyntaxException {
        val sourceLocation: SourceLocation = AntlrHelper.createSourceLocation(
            multiSourceReader = multiSourceReader,
            token = token
        )
        val sourcePreview = createPreview(
            multiSourceReader = multiSourceReader,
            antrlLine = token.line
        )

        return MoreTokensSyntaxException(
            i18N = environment.i18N,
            sourceLocation = sourceLocation,
            offendingToken = token.text ?: "",
            sourcePreview = sourcePreview
        )
    }

    private fun makeException(recognizer: Parser, cause: RecognitionException?): InvalidSyntaxException {
        val sourcePreview: String?
        val offendingToken: String?
        val sourceLocation: SourceLocation?
        val currentToken = recognizer.currentToken

        if (currentToken != null) {
            sourceLocation = AntlrHelper.createSourceLocation(
                multiSourceReader, currentToken
            )
            offendingToken = currentToken.text
            sourcePreview = createPreview(multiSourceReader, currentToken.line)
        } else {
            sourcePreview = null
            offendingToken = null
            sourceLocation = null
        }

        val msgKey: String
        val args: List<Any?>
        val location: SourceLocation = sourceLocation ?: SourceLocation(
            line = -1,
            column = -1
        )

        if (offendingToken == null) {
            msgKey = "InvalidSyntaxBail.noToken"
            args = ImmutableList.of(location.line, location.column)
        } else {
            msgKey = "InvalidSyntaxBail.full"
            args = ImmutableList.of(offendingToken, location.line, location.column)
        }

        val msg = environment.i18N.msg(msgKey, args)

        return InvalidSyntaxException(msg, sourceLocation, offendingToken, sourcePreview, cause)
    }
}
