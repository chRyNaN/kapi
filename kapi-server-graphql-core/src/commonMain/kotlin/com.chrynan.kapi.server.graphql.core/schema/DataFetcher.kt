package com.chrynan.kapi.server.graphql.core.schema

import com.chrynan.kapi.server.graphql.core.model.GraphQLEnvironment

fun interface DataFetcher<T> {

    suspend fun fetch(environment: GraphQLEnvironment): T
}

suspend operator fun <T> DataFetcher<T>.invoke(environment: GraphQLEnvironment): T =
    fetch(environment = environment)
