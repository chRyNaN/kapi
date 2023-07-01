package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.Serializable

/**
 * Represents a definition of a GraphQL item.
 */
@Serializable
sealed interface Definition : Node
