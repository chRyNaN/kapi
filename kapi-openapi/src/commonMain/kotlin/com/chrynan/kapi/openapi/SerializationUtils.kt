@file:Suppress("unused")

package com.chrynan.kapi.openapi

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer

/**
 * Retrieves the [SerialDescriptor] for the reified generic type and introspects the type to construct and return an
 * Open API [Schema].
 */
@ExperimentalSerializationApi
inline fun <reified T> SerializersModule.openApiSchema(): Schema {
    val descriptor = this.serializer<T>().descriptor

    return openApiSchema(serializersModule = this, descriptor = descriptor)
}

@PublishedApi
@ExperimentalSerializationApi
internal fun openApiSchema(serializersModule: SerializersModule, descriptor: SerialDescriptor): Schema =
    when (descriptor.kind) {
        is SerialKind.ENUM -> enumSchema(descriptor = descriptor)
        is SerialKind.CONTEXTUAL -> contextualSchema(descriptor = descriptor, serializersModule = serializersModule)
        is PrimitiveKind -> primitiveSchema(descriptor = descriptor)
        is StructureKind.LIST -> listSchema(descriptor = descriptor, serializersModule = serializersModule)
        is StructureKind.MAP -> mapSchema(descriptor = descriptor, serializersModule = serializersModule)
        is StructureKind.OBJECT -> Schema()
        is StructureKind.CLASS -> classSchema(descriptor = descriptor, serializersModule = serializersModule)
        is PolymorphicKind.OPEN -> openSchema(descriptor = descriptor, serializersModule = serializersModule)
        is PolymorphicKind.SEALED -> sealedSchema(descriptor = descriptor, serializersModule = serializersModule)
    }

@ExperimentalSerializationApi
internal fun primitiveSchema(descriptor: SerialDescriptor): Schema {
    require(descriptor.kind is PrimitiveKind) { "SerialDescriptor must have a kind value of PrimitiveKind to be converted to a primitive Schema." }

    val type = when (descriptor.kind) {
        PrimitiveKind.BOOLEAN -> DataType.BOOLEAN
        PrimitiveKind.BYTE -> DataType.INTEGER
        PrimitiveKind.SHORT -> DataType.INTEGER
        PrimitiveKind.INT -> DataType.INTEGER
        PrimitiveKind.LONG -> DataType.INTEGER
        PrimitiveKind.FLOAT -> DataType.NUMBER
        PrimitiveKind.DOUBLE -> DataType.NUMBER
        PrimitiveKind.CHAR -> DataType.STRING
        PrimitiveKind.STRING -> DataType.STRING
        else -> null
    }

    val format = when (descriptor.kind) {
        PrimitiveKind.FLOAT -> "float"
        PrimitiveKind.DOUBLE -> "double"
        PrimitiveKind.INT -> "int32"
        PrimitiveKind.LONG -> "int64"
        else -> null
    }

    return Schema(
        type = type,
        format = format,
        nullable = descriptor.isNullable
    )
}

@ExperimentalSerializationApi
internal fun listSchema(descriptor: SerialDescriptor, serializersModule: SerializersModule): Schema {
    require(descriptor.kind is StructureKind.LIST) { "SerialDescriptor must have a kind value of StructureKind.LIST to be converted to an Array Schema." }

    val itemSchemas = descriptor.elementDescriptors.map {
        openApiSchema(serializersModule = serializersModule, descriptor = it)
    }

    val items = when {
        itemSchemas.isEmpty() -> Schema()
        itemSchemas.size == 1 -> itemSchemas.first()
        else -> Schema(oneOf = itemSchemas)
    }

    return Schema(
        type = DataType.ARRAY,
        items = items,
        nullable = descriptor.isNullable
    )
}

@ExperimentalSerializationApi
internal fun mapSchema(descriptor: SerialDescriptor, serializersModule: SerializersModule): Schema {
    require(descriptor.kind is StructureKind.MAP) { "SerialDescriptor must have a kind value of StructureKind.MAP to be converted to a Map Schema." }

    val genericTypeDescriptors = descriptor.elementDescriptors
    val keyDescriptor = genericTypeDescriptors.first()
    val valueDescriptor = genericTypeDescriptors.last()

    val additionalProperties = if (keyDescriptor.kind == PrimitiveKind.STRING && !keyDescriptor.isNullable) {
        val valueSchema = openApiSchema(serializersModule = serializersModule, descriptor = valueDescriptor)

        Json.encodeToJsonElement(serializer = Schema.serializer(), value = valueSchema)
    } else {
        JsonPrimitive(true)
    }

    return Schema(
        type = DataType.OBJECT,
        additionalProperties = additionalProperties,
        nullable = descriptor.isNullable
    )
}

@ExperimentalSerializationApi
internal fun enumSchema(descriptor: SerialDescriptor): Schema {
    require(descriptor.kind == SerialKind.ENUM) { "SerialDescriptor must have a kind value of SerialKind.ENUM to be converted to an Enum Schema." }

    val enumValues = (0 until descriptor.elementsCount).map { index ->
        JsonPrimitive(descriptor.getElementName(index))
    }

    return Schema(
        type = DataType.STRING,
        enum = if (descriptor.isNullable) enumValues + JsonNull else enumValues,
        nullable = descriptor.isNullable
    )
}

@ExperimentalSerializationApi
internal fun classSchema(descriptor: SerialDescriptor, serializersModule: SerializersModule): Schema {
    require(descriptor.kind is StructureKind.CLASS) { "SerialDescriptor must have a kind value of StructureKind.CLASS to be converted to a Class Schema." }

    val propertySchemas = (0 until descriptor.elementsCount).associate { index ->
        descriptor.getElementName(index) to openApiSchema(
            descriptor = descriptor.getElementDescriptor(index),
            serializersModule = serializersModule
        )
    }
    val requiredPropertyNames = (0 until descriptor.elementsCount)
        .filter { index -> descriptor.isElementOptional(index) }
        .map { index -> descriptor.getElementName(index) }
        .takeIf { it.isNotEmpty() }

    return Schema(
        type = DataType.OBJECT,
        properties = propertySchemas,
        required = requiredPropertyNames,
        nullable = descriptor.isNullable
    )
}

@ExperimentalSerializationApi
internal fun openSchema(descriptor: SerialDescriptor, serializersModule: SerializersModule): Schema {
    require(descriptor.kind is PolymorphicKind.OPEN) { "SerialDescriptor must have a kind value of PolymorphicKind.OPEN to be converted to a class Schema." }

    val propertySchemas = (0 until descriptor.elementsCount).associate { index ->
        descriptor.getElementName(index) to openApiSchema(
            descriptor = descriptor.getElementDescriptor(index),
            serializersModule = serializersModule
        )
    }
    val requiredPropertyNames = (0 until descriptor.elementsCount)
        .filter { index -> descriptor.isElementOptional(index) }
        .map { index -> descriptor.getElementName(index) }
        .takeIf { it.isNotEmpty() }

    return Schema(
        type = DataType.OBJECT,
        properties = propertySchemas,
        required = requiredPropertyNames,
        nullable = descriptor.isNullable
    )
}

@ExperimentalSerializationApi
internal fun sealedSchema(descriptor: SerialDescriptor, serializersModule: SerializersModule): Schema {
    require(descriptor.kind is PolymorphicKind.SEALED) { "SerialDescriptor must have a kind value of PolymorphicKind.OPEN to be converted to a class Schema." }

    // Refer to kotlinx.serialization.SealedClassSerializer
    val valueDescriptorIndex = descriptor.getElementIndex("value")
    val valueDescriptor = descriptor.getElementDescriptor(valueDescriptorIndex)
    val subclassDescriptors = valueDescriptor.elementDescriptors.toList()
    val subclassSchemas = subclassDescriptors.map {
        openApiSchema(serializersModule = serializersModule, descriptor = descriptor)
    }.takeIf { it.isNotEmpty() }

    return Schema(
        type = DataType.OBJECT,
        oneOf = subclassSchemas,
        nullable = descriptor.isNullable
    )
}

@ExperimentalSerializationApi
internal fun contextualSchema(descriptor: SerialDescriptor, serializersModule: SerializersModule): Schema {
    require(descriptor.kind == SerialKind.CONTEXTUAL) { "SerialDescriptor must have a kind value of SerialKind.CONTEXTUAL to be converted to a Contextual Schema." }

    val actualDescriptor = descriptor.capturedKClass?.let { serializersModule.getContextual(it)?.descriptor }

    return if (actualDescriptor != null) {
        openApiSchema(serializersModule = serializersModule, descriptor = actualDescriptor)
    } else {
        // Any Object Schema
        Schema()
    }
}
