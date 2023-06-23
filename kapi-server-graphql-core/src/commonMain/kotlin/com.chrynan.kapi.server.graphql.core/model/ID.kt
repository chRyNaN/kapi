package com.chrynan.kapi.server.graphql.core.model

import kotlinx.serialization.Serializable

/**
 * Represents the GraphQL ID scalar value.
 */
@Serializable
@JvmInline
value class ID(val value: String)
