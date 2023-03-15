package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a fully resolved Kotlin type usage. This is different from the [KotlinTypeUsage] because it contains
 * information about the [KotlinTypeUsage] and its associated [KotlinTypeDeclaration], as well as more resolved type
 * data, such as resolved generic type declarations. This can be useful for scenarios that you need to fully construct
 * a model tree. Note that this class will be slow to obtain as it will require resolving each of the associated types.
 */
@Serializable
data class KotlinTypeUsageWithDeclaration(
    @SerialName(value = "usage") val usage: KotlinTypeUsage,
    @SerialName(value = "declaration") val declaration: KotlinTypeDeclaration,
    @SerialName(value = "declared_generic_types") val declaredGenericTypes: List<KotlinTypeDeclaration> = emptyList()
)
