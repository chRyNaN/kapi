//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinName](index.md)/[isSimple](is-simple.md)

# isSimple

[jvm]\
val [isSimple](is-simple.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Determines whether this [KotlinName](index.md) is considered a &quot;simple name&quot;, that is a name without a qualifier (ex: &quot;SimpleName&quot; instead of &quot;com.chrynan.FullyQualifiedName&quot;). The [full](full.md) property represents the full name, whereas the [short](short.md) property represents the last part of a fully qualified name. If both the [full](full.md) and [short](short.md) values are equal, then there is no qualifier and this is considered a &quot;simple name&quot;.
