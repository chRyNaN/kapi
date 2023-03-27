@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiAuth(
    @SerialName(value = "requirements") val requirements: List<SecurityRequirement> = emptyList(),
    @SerialName(value = "concatenation") val concatenation: RequirementConcatenation = RequirementConcatenation.AND
) {

    @Serializable
    enum class RequirementConcatenation(val serialName: String) {

        @SerialName(value = "and")
        AND(serialName = "and"),

        @SerialName(value = "or")
        OR(serialName = "or");

        companion object {

            fun getBySerialName(name: String, ignoreCase: Boolean = true): RequirementConcatenation? =
                values().firstOrNull { it.serialName.equals(name, ignoreCase) }
        }
    }
}

@Serializable
data class SecurityRequirement(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "scopes") val scopes: List<String> = emptyList()
)
