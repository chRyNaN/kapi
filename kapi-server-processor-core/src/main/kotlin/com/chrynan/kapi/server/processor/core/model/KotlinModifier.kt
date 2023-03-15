@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a modifier keyword in Kotlin.
 */
@Serializable
sealed interface KotlinModifier

/**
 * Represents the available Kotlin modifier keywords that can be applied to a Kotlin type (class, interface, etc).
 */
@Serializable
enum class KotlinTypeModifier(val serialName: String) : KotlinModifier {

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

@Serializable
enum class KotlinPropertyModifier(val serialName: String) : KotlinModifier {

    @SerialName(value = "public")
    PUBLIC(serialName = "public"),

    @SerialName(value = "private")
    PRIVATE(serialName = "private"),

    @SerialName(value = "internal")
    INTERNAL(serialName = "internal"),

    @SerialName(value = "protected")
    PROTECTED(serialName = "protected"),

    @SerialName(value = "override")
    OVERRIDE(serialName = "override"),

    @SerialName(value = "lateinit")
    LATEINIT(serialName = "lateinit"),

    @SerialName(value = "inline")
    INLINE(serialName = "inline"),

    @SerialName(value = "external")
    EXTERNAL(serialName = "external"),

    @SerialName(value = "abstract")
    ABSTRACT(serialName = "abstract"),

    @SerialName(value = "final")
    FINAL(serialName = "final"),

    @SerialName(value = "open")
    OPEN(serialName = "open"),

    @SerialName(value = "const")
    CONST(serialName = "const"),

    @SerialName(value = "expect")
    EXPECT(serialName = "expect"),

    @SerialName(value = "actual")
    ACTUAL(serialName = "actual");

    companion object {

        fun getBySerialName(name: String, ignoreCase: Boolean = true): KotlinPropertyModifier? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}

@Serializable
enum class KotlinFunctionModifier(val serialName: String) : KotlinModifier {

    @SerialName(value = "public")
    PUBLIC(serialName = "public"),

    @SerialName(value = "private")
    PRIVATE(serialName = "private"),

    @SerialName(value = "internal")
    INTERNAL(serialName = "internal"),

    @SerialName(value = "protected")
    PROTECTED(serialName = "protected"),

    @SerialName(value = "override")
    OVERRIDE(serialName = "override"),

    @SerialName(value = "inline")
    INLINE(serialName = "inline"),

    @SerialName(value = "external")
    EXTERNAL(serialName = "external"),

    @SerialName(value = "abstract")
    ABSTRACT(serialName = "abstract"),

    @SerialName(value = "final")
    FINAL(serialName = "final"),

    @SerialName(value = "open")
    OPEN(serialName = "open"),

    @SerialName(value = "expect")
    EXPECT(serialName = "expect"),

    @SerialName(value = "actual")
    ACTUAL(serialName = "actual"),

    @SerialName(value = "suspend")
    SUSPEND(serialName = "suspend"),

    @SerialName(value = "tailrec")
    TAILREC(serialName = "tailrec"),

    @SerialName(value = "operator")
    OPERATOR(serialName = "operator"),

    @SerialName(value = "infix")
    INFIX(serialName = "infix");


    companion object {

        fun getBySerialName(name: String, ignoreCase: Boolean = true): KotlinFunctionModifier? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}

@Serializable
enum class KotlinParameterModifier(val serialName: String) : KotlinModifier {

    @SerialName(value = "vararg")
    VARARG(serialName = "vararg"),

    @SerialName(value = "noinline")
    NOINLINE(serialName = "noinline"),

    @SerialName(value = "crossinline")
    CROSSINLINE(serialName = "crossinline");

    companion object {

        fun getBySerialName(name: String, ignoreCase: Boolean = true): KotlinParameterModifier? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}
