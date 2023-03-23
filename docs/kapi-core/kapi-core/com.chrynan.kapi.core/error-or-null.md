//[kapi-core](../../index.md)/[com.chrynan.kapi.core](index.md)/[errorOrNull](error-or-null.md)

# errorOrNull

[common]\
suspend fun &lt;[T](error-or-null.md), [E](error-or-null.md)&gt; [Response](-response/index.md)&lt;[T](error-or-null.md)&gt;.[errorOrNull](error-or-null.md)(typeInfo: TypeInfo): [E](error-or-null.md)?

inline suspend fun &lt;[T](error-or-null.md), [E](error-or-null.md)&gt; [Response](-response/index.md)&lt;[T](error-or-null.md)&gt;.[errorOrNull](error-or-null.md)(): [E](error-or-null.md)?

Retrieves the [error](error.md) model or `null` if there was no [error](error.md).
