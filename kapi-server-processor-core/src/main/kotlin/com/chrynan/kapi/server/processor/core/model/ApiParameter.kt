@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import com.chrynan.kapi.core.annotation.*

/**
 * Represents a parameter to an API function (a function of a type annotated with the `@Api` annotation). This model
 * contains data about the Kotlin parameter declaration and API related values that are extracted from expected
 * annotations.
 *
 * Note that this is a sealed class and subclasses may contain further information about the parameter.
 *
 * @property [declaration] The [KotlinParameterDeclaration] representing the Kotlin type, name, modifiers, whether
 * there is a default value for the parameter, etc.
 * @property [value] An optional value that is provided by most, but not all, of the supported API annotations (@Path,
 * @Part, etc.) via their `value` property.
 */
@Serializable
sealed class ApiParameter {

    abstract val declaration: KotlinParameterDeclaration
    abstract val value: String?
}

/**
 * Represents an [ApiParameter] annotated with the [Path] annotation.
 *
 * @property [declaration] The [KotlinParameterDeclaration] representing the Kotlin type, name, modifiers, whether
 * there is a default value for the parameter, etc.
 * @property [value] Corresponds to the [Path.value] property value of the [Path] annotation on this parameter.
 * @property [encoded] Corresponds to the [Path.encoded] property value of the [Path] annotation on this parameter.
 */
@Serializable
@SerialName(value = "path")
data class PathParameter(
    @SerialName(value = "declaration") override val declaration: KotlinParameterDeclaration,
    @SerialName(value = "value") override val value: String,
    @SerialName(value = "encoded") val encoded: Boolean = false
) : ApiParameter()

/**
 * Represents an [ApiParameter] annotated with the [Query] annotation.
 *
 * @property [declaration] The [KotlinParameterDeclaration] representing the Kotlin type, name, modifiers, whether
 * there is a default value for the parameter, etc.
 * @property [value] Corresponds to the [Query.value] property value of the [Query] annotation on this parameter.
 * @property [encoded] Corresponds to the [Query.encoded] property value of the [Query] annotation on this parameter.
 */
@Serializable
@SerialName(value = "query")
data class QueryParameter(
    @SerialName(value = "declaration") override val declaration: KotlinParameterDeclaration,
    @SerialName(value = "value") override val value: String,
    @SerialName(value = "encoded") val encoded: Boolean = false
) : ApiParameter()

/**
 * Represents an [ApiParameter] annotated with the [Field] annotation.
 *
 * @property [declaration] The [KotlinParameterDeclaration] representing the Kotlin type, name, modifiers, whether
 * there is a default value for the parameter, etc.
 * @property [value] Corresponds to the [Field.value] property value of the [Field] annotation on this parameter.
 * @property [encoded] Corresponds to the [Field.encoded] property value of the [Field] annotation on this parameter.
 */
@Serializable
@SerialName(value = "field")
data class FieldParameter(
    @SerialName(value = "declaration") override val declaration: KotlinParameterDeclaration,
    @SerialName(value = "value") override val value: String,
    @SerialName(value = "encoded") val encoded: Boolean = false
) : ApiParameter()

/**
 * Represents an [ApiParameter] annotated with the [Part] annotation.
 *
 * @property [declaration] The [KotlinParameterDeclaration] representing the Kotlin type, name, modifiers, whether
 * there is a default value for the parameter, etc.
 * @property [value] Corresponds to the [Part.value] property value of the [Part] annotation on this parameter.
 * @property [encoding] Corresponds to the [Part.encoding] property value of the [Part] annotation on this parameter.
 */
@Serializable
@SerialName(value = "part")
data class PartParameter(
    @SerialName(value = "declaration") override val declaration: KotlinParameterDeclaration,
    @SerialName(value = "value") override val value: String,
    @SerialName(value = "encoding") val encoding: String
) : ApiParameter()

/**
 * Represents an [ApiParameter] annotated with the [Header] annotation.
 *
 * @property [declaration] The [KotlinParameterDeclaration] representing the Kotlin type, name, modifiers, whether
 * there is a default value for the parameter, etc.
 * @property [value] Corresponds to the [Header.value] property value of the [Header] annotation on this parameter.
 */
@Serializable
@SerialName(value = "header")
data class HeaderParameter(
    @SerialName(value = "declaration") override val declaration: KotlinParameterDeclaration,
    @SerialName(value = "value") override val value: String
) : ApiParameter()

/**
 * Represents an [ApiParameter] annotated with the [Body] annotation.
 *
 * @property [declaration] The [KotlinParameterDeclaration] representing the Kotlin type, name, modifiers, whether
 * there is a default value for the parameter, etc.
 */
@Serializable
@SerialName(value = "body")
data class BodyParameter(
    @SerialName(value = "declaration") override val declaration: KotlinParameterDeclaration,
    @SerialName(value = "kotlin_type") val kotlinType: KotlinTypeUsageWithDeclaration
) : ApiParameter() {

    @Transient
    override val value: String? = null
}

/**
 * Represents a function parameter that is not annotated with one of the supported annotations but contains a default
 * value so that it can still be used with an API function.
 *
 * @property [declaration] The [KotlinParameterDeclaration] representing the Kotlin type, name, modifiers, whether
 * there is a default value for the parameter, etc.
 */
@Serializable
@SerialName(value = "default")
data class DefaultValueParameter(
    @SerialName(value = "declaration") override val declaration: KotlinParameterDeclaration,
) : ApiParameter() {

    @Transient
    override val value: String? = null
}

/**
 * Represents a function parameter that is not annotated with one of the supported annotations, but is a type that is
 * supported and can be provided by the processor (ex: The ApplicationCall Ktor type).
 *
 * @property [declaration] The [KotlinParameterDeclaration] representing the Kotlin type, name, modifiers, whether
 * there is a default value for the parameter, etc.
 */
@Serializable
@SerialName(value = "supported")
data class SupportedTypeParameter(
    @SerialName(value = "declaration") override val declaration: KotlinParameterDeclaration,
) : ApiParameter() {

    @Transient
    override val value: String? = null
}
