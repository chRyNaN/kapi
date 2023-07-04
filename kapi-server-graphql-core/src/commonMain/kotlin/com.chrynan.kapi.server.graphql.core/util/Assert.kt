package com.chrynan.kapi.server.graphql.core.util

object Assert {

    fun <T> assertShouldNeverHappen(): T {
        error("Internal error: should never happen")
    }
}
