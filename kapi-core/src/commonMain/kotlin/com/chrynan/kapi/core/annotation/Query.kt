package com.chrynan.kapi.core.annotation

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Query(
    val value: String,
    val encoded: Boolean = false
)
