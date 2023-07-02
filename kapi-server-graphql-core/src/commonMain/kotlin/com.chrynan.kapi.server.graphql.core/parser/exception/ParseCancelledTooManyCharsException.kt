package com.chrynan.kapi.server.graphql.core.parser.exception

import com.chrynan.kapi.server.graphql.core.i18n.I18n

class ParseCancelledTooManyCharsException(
    i18N: I18n,
    maxCharacters: Int
) : InvalidSyntaxException(
    i18N.msg(key = "ParseCancelled.tooManyChars", maxCharacters),
    location = null,
    offendingToken = null,
    sourcePreview = null,
    cause = null
)
