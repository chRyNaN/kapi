@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.template

import com.chrynan.kapi.server.processor.core.model.ApiDefinition
import java.io.OutputStream

/**
 * Represents a template for generating a file depending on a provided [ApiDefinition].
 */
interface FileTemplate {

    /**
     * Corresponds to the relative path of the generated file; using either '.'or '/' as separator.
     */
    fun packageName(api: ApiDefinition): String

    /**
     * The name of the file.
     */
    fun fileName(api: ApiDefinition): String

    /**
     * If "kt" or "java", this file will participate in subsequent compilation. Otherwise, its creation is only
     * considered in incremental processing.
     */
    fun extensionName(api: ApiDefinition): String

    /**
     * Generates the file contents for the provided [api] using this [OutputStream].
     */
    operator fun OutputStream.invoke(api: ApiDefinition)
}
