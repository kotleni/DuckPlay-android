package kotleni.duckplay.entities

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("apiVersion") val apiVersion: Int,
    @SerializedName("isKick") val isKick: Boolean,
    @SerializedName("kickMessage") val kickMessage: String
)
