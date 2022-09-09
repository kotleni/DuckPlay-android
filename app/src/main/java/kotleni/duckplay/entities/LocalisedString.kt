package kotleni.duckplay.entities

import com.google.gson.annotations.SerializedName

data class LocalisedString(
    @SerializedName("en") val en: String,
    @SerializedName("ru") val ru: String,
    @SerializedName("ua") val ua: String
)