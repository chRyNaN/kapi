package com.chrynan.kapi.core.annotation

import com.chrynan.kapi.core.annotation.parameter.Header

annotation class ResponseHeaders(vararg val values: Header = [])
