package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KotlinPropertyDeclaration(
    @SerialName(value = "name") val name: KotlinName,
    @SerialName(value = "type") val type: KotlinTypeUsage,
    @SerialName(value = "annotations") val annotations: List<KotlinAnnotationUsage> = emptyList(),
    @SerialName(value = "modifiers") val modifiers: List<KotlinPropertyModifier> = emptyList(),
    @SerialName(value = "documentation") val documentation: String? = null,
    @SerialName(value = "extension_receiver") val extensionReceiver: KotlinTypeUsage? = null,
    @SerialName(value = "getter") val hasGetter: Boolean = false,
    @SerialName(value = "setter") val hasSetter: Boolean = false,
    @SerialName(value = "mutable") val isMutable: Boolean = false,
    @SerialName(value = "backing_field") val hasBackingField: Boolean = false,
    @SerialName(value = "delegated") val isDelegated: Boolean = false
)
