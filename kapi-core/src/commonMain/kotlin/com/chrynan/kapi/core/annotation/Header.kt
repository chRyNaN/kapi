package com.chrynan.kapi.core.annotation

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
annotation class Header(
    val value: String,
    val name: String = "",
    val safeOnly: Boolean = true,
    val onlyIfAbsent: Boolean = false
)
