//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.template](../index.md)/[TextFileTemplate](index.md)

# TextFileTemplate

[jvm]\
interface [TextFileTemplate](index.md) : [FileTemplate](../-file-template/index.md)

## Functions

| Name | Summary |
|---|---|
| [content](content.md) | [jvm]<br>abstract fun [content](content.md)(api: [ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [extensionName](../-file-template/extension-name.md) | [jvm]<br>abstract fun [extensionName](../-file-template/extension-name.md)(api: [ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>If &quot;kt&quot; or &quot;java&quot;, this file will participate in subsequent compilation. Otherwise, its creation is only considered in incremental processing. |
| [fileName](../-file-template/file-name.md) | [jvm]<br>abstract fun [fileName](../-file-template/file-name.md)(api: [ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The name of the file. |
| [invoke](invoke.md) | [jvm]<br>open operator override fun [OutputStream](https://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html).[invoke](invoke.md)(api: [ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md))<br>Generates the file contents for the provided [api](invoke.md) using this [OutputStream](https://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html). |
| [packageName](../-file-template/package-name.md) | [jvm]<br>abstract fun [packageName](../-file-template/package-name.md)(api: [ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Corresponds to the relative path of the generated file; using either '.'or '/' as separator. |

## Properties

| Name | Summary |
|---|---|
| [charset](charset.md) | [jvm]<br>open val [charset](charset.md): [Charset](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html) |
