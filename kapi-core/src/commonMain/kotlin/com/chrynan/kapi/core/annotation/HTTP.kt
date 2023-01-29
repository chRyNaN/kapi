package com.chrynan.kapi.core.annotation

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class HTTP(
    val method: String,
    val path: String,
    val hasBody: Boolean
)
