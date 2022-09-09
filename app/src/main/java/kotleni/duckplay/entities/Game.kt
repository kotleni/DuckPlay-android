package kotleni.duckplay.entities

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("name") val name: String,
    @SerializedName("about") val about: String,
    @SerializedName("url") val url: String,
    @SerializedName("fullscreen") val isFullScreen: Boolean
) {
    fun getIconUrl(): String {
        return "${url}/icon.png"
    }
}
