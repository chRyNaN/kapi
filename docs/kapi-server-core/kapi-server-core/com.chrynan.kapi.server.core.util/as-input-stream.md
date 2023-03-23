//[kapi-server-core](../../index.md)/[com.chrynan.kapi.server.core.util](index.md)/[asInputStream](as-input-stream.md)

# asInputStream

[jvm]\
fun PartData.[asInputStream](as-input-stream.md)(parent: Job? = null): [InputStream](https://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html)

Converts this PartData into an [InputStream](https://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html) if possible. If this is a PartData.FormItem instance, then an exception will be thrown as that cannot be converted into an [InputStream](https://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html).

#### Parameters

jvm

| | |
|---|---|
| parent | Provided to the ByteReadChannel.toInputStream function if this is a PartData.BinaryChannelItem instance. |
