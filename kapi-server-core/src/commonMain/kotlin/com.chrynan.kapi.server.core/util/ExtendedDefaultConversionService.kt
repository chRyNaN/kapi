package com.chrynan.kapi.server.core.util

import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import io.ktor.util.converters.*
import io.ktor.util.reflect.*
import kotlin.reflect.KClass

/**
 * An extended version of the [DefaultConversionService] class that supports [Byte] and unsigned integer types.
 */
@ExperimentalServerApi
object ExtendedDefaultConversionService : ConversionService {

    override fun toValues(value: Any?): List<String> =
        DefaultConversionService.toValues(value = value)

    override fun fromValues(values: List<String>, type: TypeInfo): Any? {
        if (values.isEmpty()) {
            return null
        }

        if (type.type == List::class || type.type == MutableList::class) {
            val argumentType = type.kotlinType?.arguments?.single()?.type?.classifier as? KClass<*>
            if (argumentType != null) {
                return values.map { DefaultConversionService.fromValue(it, argumentType) }
            }
        }

        when {
            values.isEmpty() ->
                throw DataConversionException("There are no values when trying to construct single value $type")

            values.size > 1 ->
                throw DataConversionException("There are multiple values when trying to construct single value $type")

            else -> return fromValue(values.single(), type.type)
        }
    }

    private fun fromValue(value: String, klass: KClass<*>): Any {
        val converted = convertPrimitivesNotSupportedByDefaultConversionService(klass = klass, value = value)

        if (converted != null) {
            return converted
        }

        return DefaultConversionService.fromValue(value = value, klass = klass)
    }

    private fun convertPrimitivesNotSupportedByDefaultConversionService(klass: KClass<*>, value: String) =
        when (klass) {
            Byte::class -> value.toByte()
            UByte::class -> value.toUByte()
            UShort::class -> value.toUShort()
            UInt::class -> value.toUInt()
            ULong::class -> value.toULong()
            else -> null
        }
}
