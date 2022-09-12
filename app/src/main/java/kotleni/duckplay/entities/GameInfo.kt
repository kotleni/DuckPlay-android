package kotleni.duckplay.entities

import com.google.gson.annotations.SerializedName

data class GameInfo(
    @SerializedName("files") val files: List<String>
)
