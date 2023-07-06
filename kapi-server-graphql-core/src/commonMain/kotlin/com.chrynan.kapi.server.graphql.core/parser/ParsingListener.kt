package com.chrynan.kapi.server.graphql.core.parser

/**
 * This listener interface is invoked for each token parsed by the graphql parser code.
 */
fun interface ParsingListener {

    /**
     * This is called for each token found during parsing
     *
     * @param token the token found
     */
    fun onToken(token: Token?)

    /**
     * This represents a token that has been parsed
     */
    interface Token {

        /**
         * @return the text of the parsed token
         */
        val text: String?

        /**
         * @return the line the token occurred on
         */
        val line: Int

        /**
         * @return the position within the line the token occurred on
         */
        val charPositionInLine: Int
    }

    companion object {

        /**
         * A NoOp implementation of [ParsingListener]
         */
        val NOOP: ParsingListener = ParsingListener { _: Token? -> }
    }
}
