//[kapi-server-core](../../index.md)/[com.chrynan.kapi.server.core.util](index.md)/[asInput](as-input.md)

# asInput

[jvm]\
fun PartData.[asInput](as-input.md)(parent: Job? = null, pool: ObjectPool&lt;ChunkBuffer&gt; = ChunkBuffer.Pool): Input

Converts this PartData into an Input if possible. If this is a PartData.FormItem instance, then an exception will be thrown as that cannot be converted into an Input.

#### Parameters

jvm

| | |
|---|---|
| parent | Provided to the ByteReadChannel.toInputStream function if this is a PartData.BinaryChannelItem instance. |
| pool | Provided to the InputStream.asInput function if this is a PartData.BinaryChannelItem instance. |
