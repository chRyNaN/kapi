package com.chrynan.kapi.server.graphql.core.language

sealed interface DescribedNode : Node {

    val description: Description?
}
