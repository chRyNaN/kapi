package com.chrynan.kapi.server.graphql.core.parser.exception

import com.chrynan.kapi.server.graphql.core.i18n.I18n
import com.chrynan.kapi.server.graphql.core.language.SourceLocation

class MoreTokensSyntaxException(
    i18N: I18n,
    sourceLocation: SourceLocation,
    offendingToken: String,
    sourcePreview: String
) : InvalidSyntaxException(
    message = i18N.msg(
        key = "InvalidSyntaxMoreTokens.full",
        offendingToken,
        sourceLocation.line,
        sourceLocation.column
    ),
    location = sourceLocation,
    offendingToken = offendingToken,
    sourcePreview = sourcePreview,
    cause = null
)
