//[kapi-server-core](../../index.md)/[com.chrynan.kapi.server.core.util](index.md)/[asByteReadChannel](as-byte-read-channel.md)

# asByteReadChannel

[jvm]\
fun PartData.[asByteReadChannel](as-byte-read-channel.md)(context: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) = Dispatchers.IO, pool: ObjectPool&lt;[ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)&gt; = ByteArrayPool): ByteReadChannel

Converts this PartData into a ByteReadChannel if possible. If this is a PartData.FormItem instance, then an exception will be thrown as that cannot be converted into an ByteReadChannel.

#### Parameters

jvm

| | |
|---|---|
| context | Provided to the InputStream.toByteReadChannel function if this is a PartData.FileItem or PartData.BinaryItem instance. |
| pool | Provided to the InputStream.toByteReadChannel function if this is a PartData.FileItem or PartData.BinaryItem instance. |
