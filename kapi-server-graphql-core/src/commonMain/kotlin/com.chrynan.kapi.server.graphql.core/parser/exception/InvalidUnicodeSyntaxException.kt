package com.chrynan.kapi.server.graphql.core.parser.exception

import com.chrynan.kapi.server.graphql.core.i18n.I18n
import com.chrynan.kapi.server.graphql.core.language.SourceLocation

class InvalidUnicodeSyntaxException(
    i18N: I18n,
    msgKey: String,
    sourceLocation: SourceLocation,
    offendingToken: String
) : InvalidSyntaxException(
    message = i18N.msg(key = msgKey, offendingToken, sourceLocation.line, sourceLocation.column),
    location = sourceLocation,
    offendingToken = offendingToken,
    sourcePreview = null,
    cause = null
)
