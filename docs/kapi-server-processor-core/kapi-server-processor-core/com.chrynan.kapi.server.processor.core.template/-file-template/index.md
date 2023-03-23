//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.template](../index.md)/[FileTemplate](index.md)

# FileTemplate

[jvm]\
interface [FileTemplate](index.md)

Represents a template for generating a file depending on a provided [ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md).

## Functions

| Name | Summary |
|---|---|
| [extensionName](extension-name.md) | [jvm]<br>abstract fun [extensionName](extension-name.md)(api: [ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>If &quot;kt&quot; or &quot;java&quot;, this file will participate in subsequent compilation. Otherwise, its creation is only considered in incremental processing. |
| [fileName](file-name.md) | [jvm]<br>abstract fun [fileName](file-name.md)(api: [ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The name of the file. |
| [invoke](invoke.md) | [jvm]<br>abstract operator fun [OutputStream](https://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html).[invoke](invoke.md)(api: [ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md))<br>Generates the file contents for the provided [api](invoke.md) using this [OutputStream](https://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html). |
| [packageName](package-name.md) | [jvm]<br>abstract fun [packageName](package-name.md)(api: [ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Corresponds to the relative path of the generated file; using either '.'or '/' as separator. |

## Inheritors

| Name |
|---|
| [TextFileTemplate](../-text-file-template/index.md) |
