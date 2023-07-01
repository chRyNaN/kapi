package com.chrynan.kapi.server.graphql.core.language

sealed interface SDLNamedDefinition : SDLDefinition,
    NamedNode
