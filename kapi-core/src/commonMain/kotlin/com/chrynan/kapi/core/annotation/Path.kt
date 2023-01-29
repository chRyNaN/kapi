package com.chrynan.kapi.core.annotation

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Path(
    val value: String,
    val encoded: Boolean = false
)
