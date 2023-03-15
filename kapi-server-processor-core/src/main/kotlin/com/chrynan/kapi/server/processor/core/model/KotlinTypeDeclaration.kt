@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the definition (a.k.a. declaration) of a Kotlin type, such as a class or interface. For instance, the
 * following Kotlin code example illustrates the [KotlinTypeDeclaration] of the class `Response`:
 *
 * ```kotlin
 * data class Response(...)
 * ```
 *
 * Whereas, the following Kotlin code example illustrates the [KotlinTypeUsage] of that defined type:
 *
 * ```kotlin
 * val response: Response = ...
 * ```
 *
 * Note that this component may not be exhaustive, meaning it won't contain all definition information. For instance,
 * all declarations, such as functions and internal types, are not currently specified.
 *
 * @property [name] The [KotlinName] of the type.
 * @property [annotations] Any annotations applied to this [KotlinTypeDeclaration].
 * @property [kind] Type type of component for this type definition.
 * @property [documentation] Any KDoc documentation specified on this type definition.
 * @property [modifiers] The [KotlinTypeModifier]s on this type definition.
 *
 * @see [KotlinTypeUsage] for usage of a declared Kotlin type.
 */
@Serializable
data class KotlinTypeDeclaration(
    @SerialName(value = "name") override val name: KotlinName,
    @SerialName(value = "annotations") override val annotations: List<KotlinAnnotationUsage> = emptyList(),
    @SerialName(value = "type") val kind: Kind,
    @SerialName(value = "documentation") val documentation: String? = null,
    @SerialName(value = "type_parameters") val typeParameters: List<KotlinTypeParameter> = emptyList(),
    @SerialName(value = "modifiers") val modifiers: List<KotlinTypeModifier> = emptyList(),
    @SerialName(value = "super_types") val superTypes: List<KotlinTypeUsage> = emptyList(),
    @SerialName(value = "properties") val properties: List<KotlinPropertyDeclaration> = emptyList()
) : KotlinTyped {

    /**
     * The kind of component for a [KotlinTypeDeclaration].
     */
    enum class Kind(val serialName: String) {

        INTERFACE(serialName = "interface"),
        CLASS(serialName = "class"),
        ENUM_CLASS(serialName = "enum_class"),
        ENUM_ENTRY(serialName = "enum_entry"),
        OBJECT(serialName = "object"),
        ANNOTATION_CLASS(serialName = "annotation_class");

        companion object {

            fun getBySerialName(name: String, ignoreCase: Boolean = true): Kind? =
                values().firstOrNull { it.serialName.equals(name, ignoreCase) }
        }
    }
}
