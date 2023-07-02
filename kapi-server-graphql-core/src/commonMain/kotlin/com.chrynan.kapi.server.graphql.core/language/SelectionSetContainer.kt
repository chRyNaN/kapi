package com.chrynan.kapi.server.graphql.core.language

sealed interface SelectionSetContainer {

    val selectionSet: SelectionSet?
}
