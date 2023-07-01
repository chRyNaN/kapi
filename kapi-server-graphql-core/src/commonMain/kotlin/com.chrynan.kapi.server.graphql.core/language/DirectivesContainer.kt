package com.chrynan.kapi.server.graphql.core.language

/**
 * Represents a language node that can contain Directives.  Directives can be repeatable and (by default)
 * non-repeatable.
 */
sealed interface DirectivesContainer {

    val directives: List<Directive>
}
