//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinTypeArgument](index.md)

# KotlinTypeArgument

[jvm]\
@Serializable

data class [KotlinTypeArgument](index.md)(val variance: [KotlinGenericVariance](../-kotlin-generic-variance/index.md), val type: [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null)

Represents a Kotlin type argument. Note that this represents a type argument and not a type parameter. A [KotlinTypeArgument](index.md) is the value provided to a generic function or component use-site call, whereas a type parameter is the variable used at the definition of the generic component. For example, a [KotlinTypeArgument](index.md) could be the following:

```kotlin
// The `User` as the generic type argument of the `Response` return type of the following function call is a
// "type argument":
suspend fun getUser(): Response<User>
```

#### See also

jvm

| | |
|---|---|
| [KotlinTypeParameter](../-kotlin-type-parameter/index.md) | for a type parameter instead of a type argument. |

## Constructors

| | |
|---|---|
| [KotlinTypeArgument](-kotlin-type-argument.md) | [jvm]<br>fun [KotlinTypeArgument](-kotlin-type-argument.md)(variance: [KotlinGenericVariance](../-kotlin-generic-variance/index.md), type: [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null) |

## Properties

| Name | Summary |
|---|---|
| [type](type.md) | [jvm]<br>val [type](type.md): [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null<br>The [KotlinTypeUsage](../-kotlin-type-usage/index.md) representing the Kotlin type provided as this argument. |
| [variance](variance.md) | [jvm]<br>val [variance](variance.md): [KotlinGenericVariance](../-kotlin-generic-variance/index.md)<br>The [KotlinGenericVariance](../-kotlin-generic-variance/index.md) of this type argument. |
