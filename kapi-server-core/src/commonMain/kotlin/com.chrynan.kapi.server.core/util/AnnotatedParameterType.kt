@file:Suppress("unused")

package com.chrynan.kapi.server.core.util

import com.chrynan.kapi.server.core.annotation.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
@ExperimentalServerApi
enum class ParameterAnnotationType(val serialName: String) {

    @SerialName(value = "body")
    BODY(serialName = "body"),

    @SerialName(value = "field")
    FIELD(serialName = "field"),

    @SerialName(value = "header")
    HEADER(serialName = "header"),

    @SerialName(value = "part")
    PART(serialName = "part"),

    @SerialName(value = "path")
    PATH(serialName = "path"),

    @SerialName(value = "query")
    QUERY(serialName = "query"),

    @SerialName(value = "principal")
    PRINCIPAL(serialName = "principal"),

    @SerialName(value = "supported")
    SUPPORTED(serialName = "supported");

    companion object {

        fun getBySerialName(name: String, ignoreCase: Boolean = true): ParameterAnnotationType? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}

@ExperimentalServerApi
val ParameterAnnotationType.annotationClass: KClass<*>?
    get() = when (this) {
        ParameterAnnotationType.BODY -> Body::class
        ParameterAnnotationType.FIELD -> Field::class
        ParameterAnnotationType.HEADER -> Header::class
        ParameterAnnotationType.PART -> Part::class
        ParameterAnnotationType.PATH -> Path::class
        ParameterAnnotationType.QUERY -> Query::class
        ParameterAnnotationType.PRINCIPAL -> Principal::class
        else -> null
    }

@ExperimentalServerApi
fun <A : Annotation> ParameterAnnotationType.Companion.getByAnnotationClass(kClass: KClass<A>): ParameterAnnotationType? =
    ParameterAnnotationType.values().firstOrNull { it.annotationClass == kClass }
