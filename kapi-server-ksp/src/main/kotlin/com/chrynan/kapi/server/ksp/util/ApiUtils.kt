@file:OptIn(ExperimentalServerApi::class)

package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.server.core.annotation.Contact
import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import com.chrynan.kapi.server.core.annotation.Info
import com.chrynan.kapi.server.core.annotation.License
import com.chrynan.kapi.server.processor.core.model.*

internal val ApiDefinition.apiName: String
    get() = this.name.takeIf { it.isNotBlank() } ?: this.type.name.short

internal val Info.isEmpty: Boolean
    get() = this.title.isBlank() && this.summary.isBlank() && this.version.isBlank() && this.termsOfService.isBlank() && this.contact.isEmpty && this.license.isEmpty

internal val Contact.isEmpty: Boolean
    get() = this.name.isBlank() && this.url.isBlank() && this.email.isBlank()

internal val License.isEmpty: Boolean
    get() = this.name.isBlank() && this.identifier.isBlank() && this.url.isBlank()
