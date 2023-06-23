package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Directive(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "locations") val locations: List<DirectiveLocation> = emptyList(),
    @SerialName(value = "args") val arguments: List<Argument> = emptyList()
) {

    fun copy(
        name: String = this.name,
        description: String? = this.description,
        locations: List<DirectiveLocation> = this.locations,
        arguments: List<Argument> = this.arguments
    ): Directive = Directive(
        name = name,
        description = description,
        locations = locations,
        arguments = arguments
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Directive) return false

        if (name != other.name) return false
        if (description != other.description) return false
        if (locations != other.locations) return false

        return arguments == other.arguments
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + locations.hashCode()
        result = 31 * result + arguments.hashCode()
        return result
    }

    override fun toString(): String =
        "Directive(name='$name', description=$description, locations=$locations, arguments=$arguments)"
}
