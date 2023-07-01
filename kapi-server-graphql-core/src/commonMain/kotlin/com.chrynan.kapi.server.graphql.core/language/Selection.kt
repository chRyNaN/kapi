package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.Serializable

/**
 * Represents an item that can be selected from a selection set within a GraphQL query. This can either be a Field,
 * InlineFragment, or FragmentSpread.
 */
@Serializable
sealed interface Selection : Node
