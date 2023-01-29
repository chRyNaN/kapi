package com.chrynan.kapi.core.annotation

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class QueryName(val encoded: Boolean = false)
