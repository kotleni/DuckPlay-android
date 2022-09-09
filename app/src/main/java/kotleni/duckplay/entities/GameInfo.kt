package kotleni.duckplay.entities

import com.google.gson.annotations.SerializedName

data class GameInfo(
    @SerializedName("isFullScreen") val isFullScreen: Boolean,
    @SerializedName("files") val files: List<String>
)
