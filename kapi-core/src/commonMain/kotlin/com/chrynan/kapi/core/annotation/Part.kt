package com.chrynan.kapi.core.annotation

@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Part(
    val value: String,
    val encoding: String
)
