//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinName](index.md)/[packageName](package-name.md)

# packageName

[jvm]\
val [packageName](package-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?

Attempts to retrieve the package name from this [KotlinName](index.md) instance. The package name is considered the [qualifier](qualifier.md) value until the first uppercase letter is reached. For instance, consider the [KotlinName](index.md) for the [KotlinName.full](full.md) value of `com.example.test.OuterType.InnerType`, then the [KotlinName.qualifier](qualifier.md) value would be `com.example.test.OuterType`, and the [KotlinName.packageName](package-name.md) would be `com.example.test`.

Note that if [KotlinName.isSimple](is-simple.md) returns `true`, then this will result in a `null` value.

Note that this may not return a correct result in all instances. Use with caution.
