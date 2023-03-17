package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.openapi.*
import com.chrynan.kapi.server.processor.core.model.*

internal val KotlinTyped.openApiDataType: DataType
    get() = when {
        this.isBoolean -> DataType.BOOLEAN
        this.isChar -> DataType.STRING
        this.isCharSequence -> DataType.STRING
        this.isString -> DataType.STRING
        this.isByte -> DataType.INTEGER
        this.isShort -> DataType.INTEGER
        this.isInt -> DataType.INTEGER
        this.isLong -> DataType.INTEGER
        this.isUByte -> DataType.INTEGER
        this.isUShort -> DataType.INTEGER
        this.isInt -> DataType.INTEGER
        this.isULong -> DataType.INTEGER
        this.isFloat -> DataType.NUMBER
        this.isDouble -> DataType.NUMBER
        this.isSupportedArrayType -> DataType.ARRAY
        this.isSupportedCollectionType -> DataType.ARRAY
        else -> DataType.OBJECT // FIXME: What about serializable types. For instance, it may be a kotlinx.datetime.Instant but might be serialized as a String.
    }

internal val KotlinTyped.openApiFormat: String?
    get() = when {
        this.isFloat -> "float"
        this.isDouble -> "double"
        this.isInt -> "int32"
        this.isLong -> "int64"
        this.isUInt -> "int32" // There is no support for unsigned types in Open API.
        this.isULong -> "int64" // There is no support for unsigned types in Open API.
        else -> null
    }

internal val KotlinTyped.openApiProperties: Map<String, Schema>?
    get() = (this as? KotlinTypeDeclaration)?.properties?.associate { property ->
        property.name.short to property.type.openApiSchema
    }

internal val KotlinTyped.openApiSchema: Schema
    get() = Schema(
        type = listOf(this.openApiDataType),
        format = this.openApiFormat,
        properties = this.openApiProperties
    )

fun ApiDefinition.test() {
    val types = this.functions.flatMap { function ->
        function.parameters.map { it.declaration.type } + function.responseBody?.declaration
    }.filterNotNull()

    types.forEach { type ->
        "" to Schema(
            type = listOf(type.openApiDataType)
        )
    }

    OpenApi(
        openApiVersion = "",
        info = Info(title = ""),
        components = Components(
            schemas = mapOf(
                "" to Schema(
                    type = listOf()
                )
            )
        )
    )
}
