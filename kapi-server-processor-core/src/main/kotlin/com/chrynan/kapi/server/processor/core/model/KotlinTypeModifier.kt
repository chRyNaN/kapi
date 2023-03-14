@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName

/**
 * Represents the available Kotlin modifier keywords that can be applied to a Kotlin type (class, interface, etc).
 */
enum class KotlinTypeModifier(val serialName: String) {

    @SerialName(value = "public")
    PUBLIC(serialName = "public"),

    @SerialName(value = "protected")
    PROTECTED(serialName = "protected"),

    @SerialName(value = "internal")
    INTERNAL(serialName = "internal"),

    @SerialName(value = "private")
    PRIVATE(serialName = "private"),

    @SerialName(value = "expect")
    EXPECT(serialName = "expect"),

    @SerialName(value = "actual")
    ACTUAL(serialName = "actual"),

    @SerialName(value = "enum")
    ENUM(serialName = "enum"),

    @SerialName(value = "sealed")
    SEALED(serialName = "sealed"),

    @SerialName(value = "annotation")
    ANNOTATION(serialName = "annotation"),

    @SerialName(value = "data")
    DATA(serialName = "data"),

    @SerialName(value = "inner")
    INNER(serialName = "inner"),

    @SerialName(value = "fun")
    FUN(serialName = "fun"),

    @SerialName(value = "value")
    VALUE(serialName = "value"),

    @SerialName(value = "abstract")
    ABSTRACT(serialName = "abstract"),

    @SerialName(value = "final")
    FINAL(serialName = "final"),

    @SerialName(value = "open")
    OPEN(serialName = "open");

    companion object {

        /**
         * Retrieves the [KotlinTypeModifier] by the provided serial [name] value.
         */
        fun getBySerialName(name: String, ignoreCase: Boolean = true): KotlinTypeModifier? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}
