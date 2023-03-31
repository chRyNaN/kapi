@file:Suppress("unused")

package com.chrynan.kapi.server.example.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName(value = "id") val id: String,
    @SerialName(value = "created") val created: Instant,
    @SerialName(value = "updated") val updated: Instant,
    @SerialName(value = "name") val name: String? = null,
    @SerialName(value = "labels") val labels: List<String> = emptyList(),
    @SerialName(value = "value") val value: ImageData
)

@Serializable
data class ImageData(
    @SerialName(value = "uri") val uri: String,
    @SerialName(value = "mime_type") val mimeType: String? = null,
    @SerialName(value = "static") val isStatic: Boolean = true,
    @SerialName(value = "blur_hash") val blurHash: String? = null,
    @SerialName(value = "focal_point") val focalPoint: FocalPoint? = null,
    @SerialName(value = "size") val size: Size? = null,
    @SerialName(value = "density") val density: Float? = null,
    @SerialName(value = "orientation") val orientation: ImageOrientation? = null,
    @SerialName(value = "color_palette") val colorPalette: Palette? = null
)

@Serializable
enum class ImageOrientation {

    @SerialName(value = "portrait")
    PORTRAIT,

    @SerialName(value = "landscape")
    LANDSCAPE,

    @SerialName(value = "square")
    SQUARE
}

@Serializable
data class Size(
    @SerialName(value = "width") val width: Float,
    @SerialName(value = "height") val height: Float
)

@Serializable
data class FocalPoint(
    @SerialName(value = "x") val x: Float,
    @SerialName(value = "y") val y: Float
)

@Serializable
data class RgbaColor(
    @SerialName(value = "red") val red: Int,
    @SerialName(value = "green") val green: Int,
    @SerialName(value = "blue") val blue: Int,
    @SerialName(value = "alpha") val alpha: Int
)

@Serializable
data class Swatch(
    @SerialName(value = "type") val type: Type,
    @SerialName(value = "mode") val mode: Mode? = null,
    @SerialName(value = "color") val color: RgbaColor,
    @SerialName(value = "primary_on_color") val primaryOnColor: RgbaColor,
    @SerialName(value = "secondary_on_color") val secondaryOnColor: RgbaColor,
    @SerialName(value = "population") val population: Int? = null
) {

    @Serializable
    enum class Type {

        @SerialName(value = "dominant")
        DOMINANT,

        @SerialName(value = "vibrant")
        VIBRANT,

        @SerialName(value = "muted")
        MUTED
    }

    @Serializable
    enum class Mode {

        @SerialName(value = "light")
        LIGHT,

        @SerialName(value = "dark")
        DARK
    }
}

@Serializable
data class Palette(
    @SerialName(value = "swatches") val swatches: List<Swatch> = emptyList()
)
