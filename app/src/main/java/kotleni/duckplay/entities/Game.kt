package kotleni.duckplay.entities

import android.content.res.Resources
import com.google.gson.annotations.SerializedName
import kotleni.duckplay.network.DuckplayAPI

data class Game(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: LocalisedString,
    @SerializedName("about") val about: LocalisedString,
) {
    fun getWebUrl(): String {
        return "${DuckplayAPI.baseUrl}/duckplay/games/${id}"
    }

    fun getIconUrl(): String {
        return "${getWebUrl()}/icon.png"
    }

    fun getJsonUrl(): String {
        return "${getWebUrl()}/game.json"
    }

    fun getName(): String {
        val locale = Resources.getSystem().getConfiguration().locale
        println("your lang: " + locale.language)

        when(locale.language) {
            "ru" -> { return name.ru }
            "uk" -> { return name.ua }
            else -> { return name.en }
        }
    }

    fun getAbout(): String {
        val locale = Resources.getSystem().getConfiguration().locale

        when(locale.language) {
            "ru" -> { return about.ru }
            "uk" -> { return about.ua }
            else -> { return about.en }
        }
    }
}
