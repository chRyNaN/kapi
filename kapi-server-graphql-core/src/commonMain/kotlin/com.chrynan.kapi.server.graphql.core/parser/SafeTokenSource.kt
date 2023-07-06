package com.chrynan.kapi.server.graphql.core.parser

import org.antlr.v4.kotlinruntime.CharStream
import org.antlr.v4.kotlinruntime.Token
import org.antlr.v4.kotlinruntime.TokenFactory
import org.antlr.v4.kotlinruntime.TokenSource

/**
 * This token source can wrap a lexer and if it asks for more than a maximum number of tokens
 * the user can take some action, typically throw an exception to stop lexing.
 *
 * It tracks the maximum number per token channel, so we have 3 at the moment, and they will all be tracked.
 *
 * This is used to protect us from evil input.  The lexer will eagerly try to find all tokens
 * at times and certain inputs (directives butted together for example) will cause the lexer
 * to keep doing work even though before the tokens are presented back to the parser
 * and hence before it has a chance to stop work once too much as been done.
 */
class SafeTokenSource(
    private val lexer: TokenSource,
    private val maxTokens: Int,
    private val maxWhitespaceTokens: Int,
    private val whenMaxTokensExceeded: (Int, Token) -> Unit,
    // this could be a Map<int,int> however we want it to be faster as possible.
    // we only have 3 channels - but they are 0,2 and 3 so use 5 for safety - still faster than a map get/put
    // if we ever add another channel beyond 5 it will IOBEx during tests so future changes will be handled before release!
    private val channelCounts: IntArray = intArrayOf(0, 0, 0, 0, 0)
) : TokenSource {

    override val line: Int
        get() = lexer.line
    override val charPositionInLine: Int
        get() = lexer.charPositionInLine
    override val sourceName: String?
        get() = lexer.sourceName
    override var tokenFactory: TokenFactory<*>
        get() = lexer.tokenFactory
        set(factory) {
            lexer.tokenFactory = factory
        }

    override fun nextToken(): Token {
        val token: Token = lexer.nextToken()

        val channel: Int = token.channel
        val currentCount = ++channelCounts[channel]
        if (channel == Parser.CHANNEL_WHITESPACE) {
            // whitespace gets its own max count
            callbackIfMaxExceeded(maxWhitespaceTokens, currentCount, token)
        } else {
            callbackIfMaxExceeded(maxTokens, currentCount, token)
        }

        return token
    }

    override fun readInputStream(): CharStream? = lexer.readInputStream()

    private fun callbackIfMaxExceeded(maxCount: Int, currentCount: Int, token: Token) {
        if (currentCount > maxCount) {
            whenMaxTokensExceeded.invoke(maxCount, token)
        }
    }
}
