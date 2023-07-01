package com.chrynan.kapi.server.graphql.core.language

sealed interface DescribedNode {

    val description: Description?
}
