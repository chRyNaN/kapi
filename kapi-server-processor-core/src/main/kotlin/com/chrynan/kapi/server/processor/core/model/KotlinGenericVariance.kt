@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Represents both declaration-site and use-site variance. [STAR] is only used and valid in use-site variance, while
 * others can be used in both.
 */
@Serializable
enum class KotlinGenericVariance(
    val serialName: String,
    val label: String
) {

    @SerialName(value = "start")
    STAR(serialName = "star", label = "*"),

    @SerialName(value = "invariant")
    INVARIANT(serialName = "invariant", label = ""),

    @SerialName(value = "covariant")
    COVARIANT(serialName = "covariant", label = "out"),

    @SerialName(value = "contravariant")
    CONTRAVARIANT(serialName = "contravariant", label = "in");

    companion object {

        fun getByName(name: String, ignoreCase: Boolean = true): KotlinGenericVariance? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}
