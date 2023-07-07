package com.chrynan.kapi.server.graphql.core.execution

import com.chrynan.kapi.server.graphql.core.introspection.__Schema
import com.chrynan.kapi.server.graphql.core.language.LiteralValue
import com.chrynan.kapi.server.graphql.core.language.OperationDefinition
import kotlinx.serialization.json.JsonElement

/**
 *
 * @see [Specification](https://spec.graphql.org/June2018/#CoerceVariableValues())
 */
fun coerceVariableValues(
    schema: __Schema,
    operation: OperationDefinition,
    variableValues: Map<String, LiteralValue>
): Map<String, LiteralValue> = TODO()
