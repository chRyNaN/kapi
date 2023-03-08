@file:Suppress("unused")

package com.chrynan.kapi.core.annotation

import kotlin.reflect.KClass

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Errors(vararg val errors: Error<*> = [])

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target()
annotation class Error<T : Throwable>(
    val statusCode: Int,
    val exception: KClass<T>,
    val type: String = "about:blank",
    val title: String = "",
    val details: String = "",
    val instance: String = "",
    val help: String = ""
)
