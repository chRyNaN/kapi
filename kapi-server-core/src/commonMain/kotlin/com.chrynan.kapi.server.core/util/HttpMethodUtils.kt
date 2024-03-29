@file:Suppress("unused")

package com.chrynan.kapi.server.core.util

import com.chrynan.kapi.core.HttpMethod
import com.chrynan.kapi.server.core.annotation.*
import kotlin.reflect.KClass

@ExperimentalServerApi
val HttpMethod.annotationClass: KClass<*>?
    get() = when (this) {
        HttpMethod.GET -> GET::class
        HttpMethod.HEAD -> HEAD::class
        HttpMethod.POST -> POST::class
        HttpMethod.PUT -> PUT::class
        HttpMethod.DELETE -> DELETE::class
        HttpMethod.OPTIONS -> OPTIONS::class
        HttpMethod.PATCH -> PATCH::class
        else -> null
    }

@ExperimentalServerApi
fun <A : Annotation> HttpMethod.Companion.getByAnnotationClass(kClass: KClass<A>): HttpMethod? =
    HttpMethod.values().firstOrNull { it.annotationClass == kClass }
