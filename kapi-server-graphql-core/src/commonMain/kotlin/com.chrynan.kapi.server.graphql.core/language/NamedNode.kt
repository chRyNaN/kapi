package com.chrynan.kapi.server.graphql.core.language

sealed interface NamedNode : Node {

    val name: String
}
