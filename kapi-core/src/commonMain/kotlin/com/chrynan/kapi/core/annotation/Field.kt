package com.chrynan.kapi.core.annotation

@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Field(
    val value: String,
    val encoded: Boolean = false
)
