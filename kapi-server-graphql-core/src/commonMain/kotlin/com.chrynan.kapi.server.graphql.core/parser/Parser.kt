package com.chrynan.kapi.server.graphql.core.parser

import com.chrynan.kapi.server.graphql.core.antlr.GraphqlLexer
import com.chrynan.kapi.server.graphql.core.antlr.GraphqlParser
import com.chrynan.kapi.server.graphql.core.language.*
import com.chrynan.kapi.server.graphql.core.parser.AntlrHelper.createPreview
import com.chrynan.kapi.server.graphql.core.parser.AntlrHelper.createSourceLocation
import com.chrynan.kapi.server.graphql.core.parser.exception.InvalidSyntaxException
import com.chrynan.kapi.server.graphql.core.parser.exception.ParseCancelledException
import com.chrynan.kapi.server.graphql.core.parser.exception.ParseCancelledTooDeepException
import com.chrynan.kapi.server.graphql.core.parser.exception.ParseCancelledTooManyCharsException
import graphql.com.google.common.collect.ImmutableList
import okio.Source
import org.antlr.v4.kotlinruntime.*
import org.antlr.v4.kotlinruntime.atn.PredictionMode
import org.antlr.v4.kotlinruntime.tree.ErrorNode
import org.antlr.v4.kotlinruntime.tree.ParseTreeListener
import org.antlr.v4.kotlinruntime.tree.TerminalNode
import java.io.IOException
import java.io.UncheckedIOException


/**
 * This can parse graphql syntax, both Query syntax and Schema Definition Language (SDL) syntax, into an Abstract
 * Syntax Tree (AST) represented by a [Document].
 *
 * Graphql syntax has a series of characters, such as spaces, new lines and commas that are not considered relevant to
 * the syntax. However, they can be captured and associated with the AST elements they belong to.
 *
 * This costs more memory but for certain use cases (like editors) this maybe be useful. We have chosen to not capture
 * ignored characters by default, but you can turn this on, either per parse or statically via
 * [ParserOptions.defaultParserOptions]
 *
 * @see [IgnoredChars] for more details about retention of characters not relevant to the GraphQL syntax.
 */
interface Parser {

    /**
     * Parses a [String] input into a graphql AST [Value].
     *
     * @param input the input to parse
     *
     * @return an AST [Value]
     *
     * @throws InvalidSyntaxException if the input is not valid graphql syntax
     */
    @Throws(InvalidSyntaxException::class)
    fun parseValue(input: String): Value

    /**
     * Parses a [String] input into a graphql AST Type.
     *
     * @param input the input to parse
     *
     * @return an AST [Type]
     *
     * @throws InvalidSyntaxException if the input is not valid graphql syntax
     */
    @Throws(InvalidSyntaxException::class)
    fun parseType(input: String): Type

    /**
     * Parses document text into a graphql AST [Document].
     *
     * @param environment the parser environment to use
     *
     * @return an AST [Document]
     *
     * @throws InvalidSyntaxException if the input is not valid graphql syntax
     */
    @Throws(InvalidSyntaxException::class)
    fun parseDocument(environment: ParserEnvironment): Document

    companion object {

        internal const val CHANNEL_COMMENTS = 2
        internal const val CHANNEL_WHITESPACE = 3
    }
}

/**
 * Parses a [String] input into a graphql AST [Document].
 *
 * @param input the input to parse
 *
 * @return an AST [Document]
 *
 * @throws InvalidSyntaxException if the input is not valid graphql syntax
 */
@Throws(InvalidSyntaxException::class)
fun Parser.parseDocument(input: String): Document {
    val source = MultiSourceReader(
        sourceParts = listOf(MultiSourceReader.SourcePart(source = input))
    )

    return parseDocument(source)
}

/**
 * Parses [Source] input into a graphql AST [Document].
 *
 * @param source the [Source] input to parse
 *
 * @return an AST [Document]
 *
 * @throws InvalidSyntaxException if the input is not valid graphql syntax
 */
@Throws(InvalidSyntaxException::class)
fun Parser.parseDocument(source: Source): Document {
    val parserEnvironment = ParserEnvironment(
        document = source
    )

    return parseDocument(parserEnvironment)
}

/**
 * Parses a [String] input into a graphql AST [Value], or `null` if an [InvalidSyntaxException] was encountered.
 *
 * @param input the input to parse.
 *
 * @return an AST [Value] or `null`.
 */
fun Parser.parseValueOrNull(input: String): Value? = try {
    parseValue(input)
} catch (e: InvalidSyntaxException) {
    null
}

/**
 * Parses a [String] input into a graphql AST Type, or `null` if an [InvalidSyntaxException] was encountered.
 *
 * @param input the input to parse.
 *
 * @return an AST [Type] or `null`.
 */
fun Parser.parseTypeOrNull(input: String): Type? = try {
    parseType(input)
} catch (e: InvalidSyntaxException) {
    null
}

/**
 * Parses document text into a graphql AST [Document], or `null` if an [InvalidSyntaxException] was encountered.
 *
 * @param environment the parser environment to use
 *
 * @return an AST [Document] or `null`.
 */
fun Parser.parseDocumentOrNull(environment: ParserEnvironment): Document? = try {
    parseDocument(environment)
} catch (e: InvalidSyntaxException) {
    null
}

/**
 * Parses document text into a graphql AST [Document], or `null` if an [InvalidSyntaxException] was encountered.
 *
 * @param input the input to parse
 *
 * @return an AST [Document] or `null`.
 */
fun Parser.parseDocumentOrNull(input: String): Document? = try {
    parseDocument(input)
} catch (e: InvalidSyntaxException) {
    null
}

/**
 * Parses document text into a graphql AST [Document], or `null` if an [InvalidSyntaxException] was encountered.
 *
 * @param source the [Source] input to parse
 *
 * @return an AST [Document] or `null`.
 */
fun Parser.parseDocumentOrNull(source: Source): Document? = try {
    parseDocument(source)
} catch (e: InvalidSyntaxException) {
    null
}

/**
 * Creates a default instance of a [Parser].
 */
fun Parser(): Parser = DefaultParser()

private class DefaultParser : Parser {

    override fun parseValue(input: String): Value {
        val nodeFunction: (GraphqlParser, GraphqlAntlrToLanguage) -> Array<Any?> =
            { parser: GraphqlParser, toLanguage: GraphqlAntlrToLanguage ->
                val documentContext: GraphqlParser.ValueContext = parser.value()
                val value: Value = toLanguage.createValue(documentContext)

                arrayOf(documentContext, value)
            }

        val multiSourceReader = MultiSourceReader(
            sourceParts = listOf(MultiSourceReader.SourcePart(source = input)),
            trackData = true
        )
        val parserEnvironment = ParserEnvironment(document = multiSourceReader)

        return parseImpl(parserEnvironment, nodeFunction) as Value
    }

    override fun parseType(input: String): Type {
        val nodeFunction: (GraphqlParser, GraphqlAntlrToLanguage) -> Array<Any?> =
            { parser: GraphqlParser, toLanguage: GraphqlAntlrToLanguage ->
                val documentContext: GraphqlParser.TypeContext = parser.type()
                val value: Type? = toLanguage.createType(documentContext)

                arrayOf(documentContext, value)
            }
        val multiSourceReader = MultiSourceReader(
            sourceParts = listOf(MultiSourceReader.SourcePart(source = input)),
            trackData = true
        )
        val parserEnvironment = ParserEnvironment(document = multiSourceReader)

        return parseImpl(parserEnvironment, nodeFunction) as Type
    }

    override fun parseDocument(environment: ParserEnvironment): Document {
        val nodeFunction: (GraphqlParser, GraphqlAntlrToLanguage) -> Array<Any?> =
            { parser: GraphqlParser, toLanguage: GraphqlAntlrToLanguage ->
                val documentContext: GraphqlParser.DocumentContext = parser.document()
                val doc = toLanguage.createDocument(documentContext)

                arrayOf(documentContext, doc)
            }

        return parseImpl(environment, nodeFunction) as Document
    }

    @Throws(InvalidSyntaxException::class)
    private fun parseImpl(
        environment: ParserEnvironment,
        nodeFunction: (GraphqlParser, GraphqlAntlrToLanguage) -> Array<Any?>
    ): Node? {
        // default in the parser options if they are not set
        val parserOptions = environment.parserOptions
        val multiSourceReader = setupMultiSourceReader(environment, parserOptions)
        val safeTokenReader: SafeTokenReader = setupSafeTokenReader(environment, parserOptions, multiSourceReader)
        val charStream = setupCharStream(safeTokenReader)
        val lexer: GraphqlLexer = setupGraphqlLexer(environment, multiSourceReader, charStream)

        // this lexer wrapper allows us to stop lexing when too many tokens are in place.  This prevents DOS attacks.
        val safeTokenSource: SafeTokenSource = getSafeTokenSource(environment, parserOptions, multiSourceReader, lexer)
        val tokens = CommonTokenStream(safeTokenSource)
        val parser = GraphqlParser(tokens)
        parser.removeErrorListeners()
        parser.interpreter?.predictionMode = PredictionMode.SLL
        val bailStrategy = ExtendedBailStrategy(
            multiSourceReader = multiSourceReader,
            environment = environment
        )
        parser.errorHandler = bailStrategy

        // preserve old protected call semantics - remove at some point
        val toLanguage = getAntlrToLanguage(tokens, multiSourceReader, environment)
        setupParserListener(environment, multiSourceReader, parser, toLanguage)


        //
        // parsing starts ...... now!
        //
        val contextAndNode = nodeFunction.invoke(parser, toLanguage)
        val parserRuleContext: ParserRuleContext? = contextAndNode[0] as ParserRuleContext?
        val node: Node? = contextAndNode[1] as Node?
        val stop = parserRuleContext?.stop
        val allTokens: List<Token> =
            tokens.getTokens( // FIXME: graphql-java used getTokens(), we don't have that function.
                start = 0,
                stop = stop?.startIndex ?: 1
            ) ?: emptyList()

        if (stop != null && allTokens.isNotEmpty()) {
            val last: Token = allTokens[allTokens.size - 1]
            //
            // do we have more tokens in the stream than we consumed in the parse?
            // if yes then it's invalid.  We make sure it's the same channel
            val notEOF = last.type != Token.EOF
            val lastGreaterThanDocument: Boolean = last.tokenIndex > stop.tokenIndex
            val sameChannel = last.channel == stop.channel

            if (notEOF && lastGreaterThanDocument && sameChannel) {
                throw bailStrategy.makeMoreTokensException(last)
            }
        }

        return node
    }

    private fun setupMultiSourceReader(
        environment: ParserEnvironment,
        parserOptions: ParserOptions
    ): MultiSourceReader {
        val reader: Source = environment.document

        return if (reader is MultiSourceReader) {
            reader
        } else {
            MultiSourceReader(
                sourceParts = listOf(MultiSourceReader.SourcePart(source = reader)),
                trackData = parserOptions.isReaderTrackData
            )
        }
    }

    private fun setupSafeTokenReader(
        environment: ParserEnvironment,
        parserOptions: ParserOptions,
        multiSourceReader: MultiSourceReader
    ): SafeTokenReader {
        val maxCharacters = parserOptions.maxCharacters
        val onTooManyCharacters: (Long) -> Unit = {
            throw ParseCancelledTooManyCharsException(
                environment.i18N,
                maxCharacters
            )
        }

        return SafeTokenReader(
            delegate = multiSourceReader,
            maxCharacters = maxCharacters.toLong(),
            whenMaxCharactersExceeded = onTooManyCharacters
        )
    }

    private fun setupCharStream(safeTokenReader: SafeTokenReader): CharStream = try {
        CharStreams.fromSource(safeTokenReader)
    } catch (e: IOException) {
        throw UncheckedIOException(e)
    }

    private fun setupGraphqlLexer(
        environment: ParserEnvironment,
        multiSourceReader: MultiSourceReader,
        charStream: CharStream
    ): GraphqlLexer {
        val lexer = GraphqlLexer(charStream)

        lexer.removeErrorListeners()
        lexer.addErrorListener(object : BaseErrorListener() {

            override fun syntaxError(
                recognizer: Recognizer<*, *>,
                offendingSymbol: Any?,
                line: Int,
                charPositionInLine: Int,
                msg: String,
                e: RecognitionException?
            ) {
                val sourceLocation = createSourceLocation(multiSourceReader, line, charPositionInLine)
                val preview = createPreview(multiSourceReader, line)
                val msgKey: String
                val args: List<Any?>

                if (msg.isBlank()) {
                    msgKey = "InvalidSyntax.noMessage"
                    args = ImmutableList.of(sourceLocation.line, sourceLocation.column)
                } else {
                    msgKey = "InvalidSyntax.full"
                    args = ImmutableList.of(msg, sourceLocation.line, sourceLocation.column)
                }

                val localizedMessage = environment.i18N.msg(msgKey, args)

                throw InvalidSyntaxException(localizedMessage, sourceLocation, null, preview, null)
            }
        })

        return lexer
    }

    private fun getSafeTokenSource(
        environment: ParserEnvironment,
        parserOptions: ParserOptions,
        multiSourceReader: MultiSourceReader,
        lexer: GraphqlLexer
    ): SafeTokenSource {
        val maxTokens = parserOptions.maxTokens
        val maxWhitespaceTokens = parserOptions.maxWhitespaceTokens
        val onTooManyTokens: (Int, Token) -> Unit =
            { maxTokenCount: Int, token: Token? ->
                throwIfTokenProblems(
                    environment,
                    token,
                    maxTokenCount,
                    multiSourceReader,
                    ParseCancelledException::class.java
                )
            }

        return SafeTokenSource(lexer, maxTokens, maxWhitespaceTokens, onTooManyTokens)
    }

    private fun setupParserListener(
        environment: ParserEnvironment,
        multiSourceReader: MultiSourceReader,
        parser: GraphqlParser,
        toLanguage: GraphqlAntlrToLanguage
    ) {
        val parserOptions: ParserOptions = toLanguage.parserOptions
        val parsingListener = parserOptions.parsingListener
        val maxTokens = parserOptions.maxTokens
        val maxRuleDepth = parserOptions.maxRuleDepth

        // prevent a billion laugh attacks by restricting how many tokens we allow
        val listener: ParseTreeListener = object : ParseTreeListener {
            var count = 0
            var depth = 0

            override fun enterEveryRule(ctx: ParserRuleContext) {
                depth++
                if (depth > maxRuleDepth) {
                    throwIfTokenProblems(
                        environment,
                        ctx.start,
                        maxRuleDepth,
                        multiSourceReader,
                        ParseCancelledTooDeepException::class.java
                    )
                }
            }

            override fun exitEveryRule(ctx: ParserRuleContext) {
                depth--
            }

            override fun visitErrorNode(node: ErrorNode) {
            }

            override fun visitTerminal(node: TerminalNode) {
                val token = node.symbol

                parsingListener.onToken(object : ParsingListener.Token {

                    override val text: String?
                        get() = token?.text

                    override val line: Int
                        get() = token?.line ?: -1

                    override val charPositionInLine: Int
                        get() = token?.charPositionInLine ?: -1
                })

                count++

                if (count > maxTokens) {
                    throwIfTokenProblems(
                        environment,
                        token,
                        maxTokens,
                        multiSourceReader,
                        ParseCancelledException::class.java
                    )
                }
            }
        }

        parser.addParseListener(listener)
    }

    @Throws(ParseCancelledException::class)
    private fun throwIfTokenProblems(
        environment: ParserEnvironment,
        token: Token?,
        maxLimit: Int,
        multiSourceReader: MultiSourceReader,
        targetException: Class<out InvalidSyntaxException?>
    ) {
        var tokenType = "grammar"
        var sourceLocation: SourceLocation? = null
        var offendingToken: String? = null

        if (token != null) {
            val channel: Int = token.channel
            tokenType =
                if (channel == Parser.CHANNEL_WHITESPACE) "whitespace" else if (channel == Parser.CHANNEL_COMMENTS) "comments" else "grammar"
            offendingToken = token.text
            sourceLocation = createSourceLocation(multiSourceReader, token.line, token.charPositionInLine)
        }

        if (targetException == ParseCancelledTooDeepException::class.java) {
            throw ParseCancelledTooDeepException(
                environment.i18N, sourceLocation, offendingToken!!, maxLimit,
                tokenType
            )
        }

        throw ParseCancelledException(environment.i18N, sourceLocation, offendingToken!!, maxLimit, tokenType)
    }

    /**
     * Allows you to override the ANTLR to AST code.
     *
     * @param tokens            the token stream
     * @param multiSourceReader the source of the query document
     * @param environment       the parser environment
     *
     * @return a new GraphqlAntlrToLanguage instance
     */
    private fun getAntlrToLanguage(
        tokens: CommonTokenStream,
        multiSourceReader: MultiSourceReader,
        environment: ParserEnvironment
    ): GraphqlAntlrToLanguage = GraphqlAntlrToLanguage(
        tokens = tokens,
        multiSourceReader = multiSourceReader,
        parserOptions = environment.parserOptions,
        i18N = environment.i18N
    )
}
