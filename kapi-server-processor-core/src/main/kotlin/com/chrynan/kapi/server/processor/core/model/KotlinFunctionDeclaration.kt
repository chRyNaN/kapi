@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KotlinFunctionDeclaration(
    @SerialName(value = "name") val name: KotlinName,
    @SerialName(value = "annotations") val annotations: List<KotlinAnnotationUsage> = emptyList(),
    @SerialName(value = "modifiers") val modifiers: List<KotlinFunctionModifier> = emptyList(),
    @SerialName(value = "kind") val kind: Kind,
    @SerialName(value = "extension_receiver") val extensionReceiver: KotlinTypeUsage? = null,
    @SerialName(value = "return_type") val returnType: KotlinTypeUsage? = null,
    @SerialName(value = "parameters") val parameters: List<KotlinParameterDeclaration> = emptyList(),
    @SerialName(value = "constructor") val isConstructor: Boolean = false
) {

    @Serializable
    enum class Kind(val serialName: String) {

        @SerialName(value = "top_level")
        TOP_LEVEL(serialName = "top_level"),

        @SerialName(value = "member")
        MEMBER(serialName = "member"),

        @SerialName(value = "static")
        STATIC(serialName = "static"),

        @SerialName(value = "anonymous")
        ANONYMOUS(serialName = "anonymous"),

        @SerialName(value = "lambda")
        LAMBDA(serialName = "lambda");

        companion object {

            fun getBySerialName(name: String, ignoreCase: Boolean = true): Kind? =
                values().firstOrNull { it.serialName.equals(name, ignoreCase) }
        }
    }
}
