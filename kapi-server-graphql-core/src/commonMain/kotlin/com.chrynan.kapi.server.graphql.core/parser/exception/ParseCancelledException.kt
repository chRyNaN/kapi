package com.chrynan.kapi.server.graphql.core.parser.exception

import com.chrynan.kapi.server.graphql.core.i18n.I18n
import com.chrynan.kapi.server.graphql.core.language.SourceLocation

class ParseCancelledException(
    i18N: I18n,
    sourceLocation: SourceLocation?,
    offendingToken: String,
    maxTokens: Int,
    tokenType: String
) : InvalidSyntaxException(
    message = i18N.msg(key = "ParseCancelled.full", maxTokens, tokenType),
    location = sourceLocation,
    offendingToken = offendingToken,
    sourcePreview = null,
    cause = null
)
