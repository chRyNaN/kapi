package com.chrynan.kapi.server.graphql.core.parser

/**
 * Options that control how the [Parser] behaves.
 */
class ParserOptions(
    /**
     * Significant memory savings can be made if we do NOT capture ignored characters,
     * especially in SDL parsing.  So we have set this to false by default.
     *
     * @return true if ignored chars should be captured as AST nodes
     */
    val isCaptureIgnoredChars: Boolean = false,
    /**
     * Memory savings can be made if we do NOT set [graphql.language.SourceLocation]s
     * on AST nodes,  especially in SDL parsing.
     *
     * @return true if [graphql.language.SourceLocation]s should be captured as AST nodes
     *
     * @see graphql.language.SourceLocation
     */
    val isCaptureSourceLocation: Boolean = true,
    /**
     * Single-line [graphql.language.Comment]s do not have any semantic meaning in
     * GraphQL source documents, as such you may wish to ignore them.
     *
     *
     * This option does not ignore documentation [graphql.language.Description]s.
     *
     * @return true if [graphql.language.Comment]s should be captured as AST nodes
     *
     * @see graphql.language.SourceLocation
     */
    val isCaptureLineComments: Boolean = true,
    /**
     * Controls whether the underlying [MultiSourceReader] should track previously read data or not.
     *
     * @return true if [MultiSourceReader] should track data in memory.
     */
    val isReaderTrackData: Boolean = true,
    /**
     * A graphql hacking vector is to send nonsensical queries that contain a repeated characters that burn lots of parsing CPU time and burn
     * memory representing a document that won't ever execute.  To prevent this for most users, graphql-java
     * sets this value to 1MB.
     *
     * @return the maximum number of characters the parser will accept, after which an exception will be thrown.
     */
    val maxCharacters: Int = MAX_QUERY_CHARACTERS,
    /**
     * A graphql hacking vector is to send nonsensical queries that burn lots of parsing CPU time and burns
     * memory representing a document that won't ever execute.  To prevent this you can set a maximum number of parse
     * tokens that will be accepted before an exception is thrown and the parsing is stopped.
     *
     * @return the maximum number of raw tokens the parser will accept, after which an exception will be thrown.
     */
    val maxTokens: Int = MAX_QUERY_TOKENS,
    /**
     * A graphql hacking vector is to send larges amounts of whitespace that burn lots of parsing CPU time and burn
     * memory representing a document.  To prevent this you can set a maximum number of whitespace parse
     * tokens that will be accepted before an exception is thrown and the parsing is stopped.
     *
     * @return the maximum number of raw whitespace tokens the parser will accept, after which an exception will be thrown.
     */
    val maxWhitespaceTokens: Int = MAX_WHITESPACE_TOKENS,
    /**
     * A graphql hacking vector is to send nonsensical queries that have lots of rule depth to them which
     * can cause stack overflow exceptions during the query parsing.  To prevent this you can set a value
     * that is the maximum depth allowed before an exception is thrown and the parsing is stopped.
     *
     * @return the maximum token depth the parser will accept, after which an exception will be thrown.
     */
    val maxRuleDepth: Int = MAX_RULE_DEPTH,
    val parsingListener: ParsingListener = ParsingListener.NOOP
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ParserOptions) return false

        if (isCaptureIgnoredChars != other.isCaptureIgnoredChars) return false
        if (isCaptureSourceLocation != other.isCaptureSourceLocation) return false
        if (isCaptureLineComments != other.isCaptureLineComments) return false
        if (isReaderTrackData != other.isReaderTrackData) return false
        if (maxCharacters != other.maxCharacters) return false
        if (maxTokens != other.maxTokens) return false
        if (maxWhitespaceTokens != other.maxWhitespaceTokens) return false
        if (maxRuleDepth != other.maxRuleDepth) return false

        return parsingListener == other.parsingListener
    }

    override fun hashCode(): Int {
        var result = isCaptureIgnoredChars.hashCode()
        result = 31 * result + isCaptureSourceLocation.hashCode()
        result = 31 * result + isCaptureLineComments.hashCode()
        result = 31 * result + isReaderTrackData.hashCode()
        result = 31 * result + maxCharacters
        result = 31 * result + maxTokens
        result = 31 * result + maxWhitespaceTokens
        result = 31 * result + maxRuleDepth
        result = 31 * result + parsingListener.hashCode()
        return result
    }

    override fun toString(): String =
        "ParserOptions(" +
                "isCaptureIgnoredChars=$isCaptureIgnoredChars, " +
                "isCaptureSourceLocation=$isCaptureSourceLocation, " +
                "isCaptureLineComments=$isCaptureLineComments, " +
                "isReaderTrackData=$isReaderTrackData, " +
                "maxCharacters=$maxCharacters, " +
                "maxTokens=$maxTokens, " +
                "maxWhitespaceTokens=$maxWhitespaceTokens, " +
                "maxRuleDepth=$maxRuleDepth, " +
                "parsingListener=$parsingListener)"

    companion object {
        /**
         * A graphql hacking vector is to send nonsensical queries with large tokens that contain a repeated characters
         * that burn lots of parsing CPU time and burn memory representing a document that won't ever execute.
         * To prevent this for most users, graphql-java sets this value to 1MB.
         * ANTLR parsing time is linear to the number of characters presented.  The more you
         * allow the longer it takes.
         *
         *
         * If you want to allow more, then [.setDefaultParserOptions] allows you to change this
         * JVM wide.
         */
        const val MAX_QUERY_CHARACTERS = 1024 * 1024 // 1 MB

        /**
         * A graphql hacking vector is to send nonsensical queries with lots of tokens that burn lots of parsing CPU time and burn
         * memory representing a document that won't ever execute.  To prevent this for most users, graphql-java
         * sets this value to 15000.  ANTLR parsing time is linear to the number of tokens presented.  The more you
         * allow the longer it takes.
         *
         *
         * If you want to allow more, then [.setDefaultParserOptions] allows you to change this
         * JVM wide.
         */
        const val MAX_QUERY_TOKENS = 15000

        /**
         * Another graphql hacking vector is to send large amounts of whitespace in operations that burn lots of parsing CPU time and burn
         * memory representing a document.  Whitespace token processing in ANTLR is 2 orders of magnitude faster than grammar token processing
         * however it still takes some time to happen.
         *
         *
         * If you want to allow more, then [.setDefaultParserOptions] allows you to change this
         * JVM wide.
         */
        const val MAX_WHITESPACE_TOKENS = 200000

        /**
         * A graphql hacking vector is to send nonsensical queries that have lots of grammar rule depth to them which
         * can cause stack overflow exceptions during the query parsing.  To prevent this for most users, graphql-java
         * sets this value to 500 grammar rules deep.
         *
         *
         * If you want to allow more, then [.setDefaultParserOptions] allows you to change this
         * JVM wide.
         */
        const val MAX_RULE_DEPTH = 500

        /**
         * By default, the Parser will not capture ignored characters. A static holds this default
         * value in a JVM wide basis options object.
         *
         * Significant memory savings can be made if we do NOT capture ignored characters,
         * especially in SDL parsing.
         *
         * @return the static default JVM value
         *
         * @see graphql.language.IgnoredChar
         *
         * @see graphql.language.SourceLocation
         */
        var defaultParserOptions: ParserOptions? = ParserOptions(
            isCaptureIgnoredChars = false,
            isCaptureSourceLocation = true,
            isCaptureLineComments = true,
            isReaderTrackData = true,
            maxCharacters = MAX_QUERY_CHARACTERS,
            maxTokens = MAX_QUERY_TOKENS,
            maxWhitespaceTokens = MAX_WHITESPACE_TOKENS,
            maxRuleDepth = MAX_RULE_DEPTH
        )

        /**
         * By default, for operation parsing, the Parser will not capture ignored characters, and it will not capture line comments into AST
         * elements .  A static holds this default value for operation parsing in a JVM wide basis options object.
         *
         * @return the static default JVM value for operation parsing
         *
         * @see graphql.language.IgnoredChar
         *
         * @see graphql.language.SourceLocation
         */
        var defaultOperationParserOptions: ParserOptions? = ParserOptions(
            isCaptureIgnoredChars = false,
            isCaptureSourceLocation = true,
            isCaptureLineComments = false,
            isReaderTrackData = true,
            maxCharacters = MAX_QUERY_CHARACTERS,
            maxTokens = MAX_QUERY_TOKENS,
            maxWhitespaceTokens = MAX_WHITESPACE_TOKENS,
            maxRuleDepth = MAX_RULE_DEPTH
        )

        /**
         * By default, for SDL parsing, the Parser will not capture ignored characters, but it will capture line comments into AST
         * elements.  The SDL default options allow unlimited tokens and whitespace, since a DOS attack vector is
         * not commonly available via schema SDL parsing.
         *
         * A static holds this default value for SDL parsing in a JVM wide basis options object.
         *
         * @return the static default JVM value for SDL parsing
         *
         * @see graphql.language.IgnoredChar
         *
         * @see graphql.language.SourceLocation
         *
         * @see graphql.schema.idl.SchemaParser
         */
        var defaultSdlParserOptions: ParserOptions? = ParserOptions(
            isCaptureIgnoredChars = false,
            isCaptureSourceLocation = true,
            isCaptureLineComments = true,
            isReaderTrackData = true,
            maxCharacters = Int.MAX_VALUE,
            maxTokens = Int.MAX_VALUE,
            maxWhitespaceTokens = Int.MAX_VALUE,
            maxRuleDepth = Int.MAX_VALUE
        )
    }
}
