package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.server.processor.core.model.*

internal val ApiDefinition.apiName: String
    get() = this.name.takeIf { it.isNotBlank() } ?: this.typeName.short
