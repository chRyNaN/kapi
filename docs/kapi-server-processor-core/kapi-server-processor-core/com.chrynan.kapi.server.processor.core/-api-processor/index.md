//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core](../index.md)/[ApiProcessor](index.md)

# ApiProcessor

[jvm]\
interface [ApiProcessor](index.md)

A component that processes [ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md)s and performs some functionality related to them.

This component and the related models provides an abstraction layer over lower-level annotation processors and compiler plugins by providing already formed models containing data related to API generation to be processed. This abstraction layer separates the logic of generating API related components from processing lower-level, utility specific, and complex components, such as abstract syntax trees. The abstraction layer promotes separation of concerns, single responsibility, easier testability, and greater flexibility and scalability.

An [ApiProcessor](index.md) is instantiated by an annotation processor like KSP (or compiler plugin) and its [process](process.md) function is invoked for each processing round of that processor. It should be noted that no guarantee is made that the [ApiProcessor](index.md) instance used between rounds will be the same instance used in previous rounds, because of this, an [ApiProcessor](index.md) should be stateless. All round and API state information is passed to the [process](process.md) function.

## Functions

| Name | Summary |
|---|---|
| [process](process.md) | [jvm]<br>abstract fun [process](process.md)(round: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), currentApis: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md)&gt;, allApis: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiDefinition](../../com.chrynan.kapi.server.processor.core.model/-api-definition/index.md)&gt;) |
