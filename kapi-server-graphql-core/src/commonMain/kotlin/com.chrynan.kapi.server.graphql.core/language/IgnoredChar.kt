@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class IgnoredChar(
    @SerialName(value = "value") val value: String,
    @SerialName(value = "kind") val kind: Kind,
    @SerialName(value = "source_location") val sourceLocation: SourceLocation
) {

    fun copy(
        value: String = this.value,
        kind: Kind = this.kind,
        sourceLocation: SourceLocation = this.sourceLocation
    ): IgnoredChar = IgnoredChar(
        value = value,
        kind = kind,
        sourceLocation = sourceLocation
    )

    operator fun component1(): String = value

    operator fun component2(): Kind = kind

    operator fun component3(): SourceLocation = sourceLocation

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IgnoredChar) return false

        if (value != other.value) return false
        if (kind != other.kind) return false

        return sourceLocation == other.sourceLocation
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + kind.hashCode()
        result = 31 * result + sourceLocation.hashCode()
        return result
    }

    override fun toString(): String =
        "IgnoredChar(value='$value', kind=$kind, sourceLocation=$sourceLocation)"

    @Serializable
    enum class Kind(val serialName: String) {

        @SerialName(value = "SPACE")
        SPACE(serialName = "SPACE"),

        @SerialName(value = "COMMA")
        COMMA(serialName = "COMMA"),

        @SerialName(value = "TAB")
        TAB(serialName = "TAB"),

        @SerialName(value = "CR")
        CR(serialName = "CR"),

        @SerialName(value = "LF")
        LF(serialName = "LF"),

        @SerialName(value = "OTHER")
        OTHER(serialName = "OTHER");

        companion object {

            fun getBySerialName(name: String, ignoreCase: Boolean = true): Kind? =
                values().firstOrNull { it.serialName.equals(name, ignoreCase) }
        }
    }
}
