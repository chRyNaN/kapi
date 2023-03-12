package com.chrynan.kapi.openapi

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Represents the supported data types for the Open API Specification. Some data types require further information to
 * differentiate between other similar types (ex: the integer and number data types). This extra information often
 * comes as a "format" field whose supported values are further defined in the chart below.
 *
 * Data types in the OAS are based on the types supported by the JSON Schema Specification Draft 2020-12. Note that
 * integer as a type is also supported and is defined as a JSON number without a fraction or exponent part. Models are
 * defined using the Schema Object, which is a superset of JSON Schema Specification Draft 2020-12.
 *
 * As defined by the JSON Schema Validation vocabulary, data types can have an optional modifier property: format. OAS
 * defines additional formats to provide fine detail for primitive data types.
 *
 * The formats defined by the OAS are:
 *
 * | type    |	format   |	Comments                        |
 * | ------- | --------- | -------------------------------- |
 * | integer |	int32    |	signed 32 bits                  |
 * | integer |	int64    |	signed 64 bits (a.k.a long)     |
 * | number  |	float    |                                  |
 * | number  |	double   |                                  |
 * | string  |	password |	A hint to UIs to obscure input. |
 * | ------- | --------- | -------------------------------- |
 *
 * @see [Open API Specification](https://spec.openapis.org/oas/v3.1.0#data-types)
 * @see [JSON Schema Specification](https://datatracker.ietf.org/doc/html/draft-bhutton-json-schema-00#section-4.2.1)
 */
@Serializable(with = DataTypeSerializer::class)
enum class DataType(val serialName: String) {

    @SerialName(value = "null")
    NULL(serialName = "null"),

    @SerialName(value = "boolean")
    BOOLEAN(serialName = "boolean"),

    @SerialName(value = "number")
    NUMBER(serialName = "number"),

    @SerialName(value = "integer")
    INTEGER(serialName = "integer"),

    @SerialName(value = "string")
    STRING(serialName = "string"),

    @SerialName(value = "object")
    OBJECT(serialName = "object"),

    @SerialName(value = "array")
    ARRAY(serialName = "array"),

    @SerialName(value = "unknown")
    UNKNOWN(serialName = "unknown");

    companion object {

        fun getBySerialName(name: String, ignoreCase: Boolean = false): DataType? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}

/**
 * A [KSerializer] that handles the serialization and deserialization of the [DataType] enum.
 */
internal object DataTypeSerializer : KSerializer<DataType> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(serialName = "Type", kind = PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DataType) {
        encoder.encodeString(value.serialName)
    }

    override fun deserialize(decoder: Decoder): DataType =
        DataType.getBySerialName(name = decoder.decodeString(), ignoreCase = true) ?: DataType.UNKNOWN
}
