@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.template

import com.chrynan.kapi.server.processor.core.model.ApiDefinition
import java.io.BufferedWriter
import java.io.OutputStream
import java.nio.charset.Charset

interface TextFileTemplate : FileTemplate {

    val charset: Charset
        get() = Charsets.UTF_8

    override fun OutputStream.invoke(api: ApiDefinition) {
        var writer: BufferedWriter? = null

        try {
            writer = this.bufferedWriter(charset = charset)

            val content = content(api = api)

            writer.write(content)
        } finally {
            writer?.close()
            this.close()
        }
    }

    fun content(api: ApiDefinition): String
}
