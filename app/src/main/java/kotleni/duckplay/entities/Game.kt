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
        return "${DuckplayAPI.BASE_URL}/duckplay/games/${id}"
    }

    fun getIconUrl(): String {
        return "${getWebUrl()}/icon.png"
    }

    fun getJsonUrl(): String {
        return "${getWebUrl()}/game.json"
    }

    fun getName(): String {
        val locale = Resources.getSystem().configuration.locale
        println("your lang: " + locale.language)

        return when(locale.language) {
            "ru" -> name.ru
            "uk" -> name.ua
            else -> name.en
        }
    }

    fun getAbout(): String {
        val locale = Resources.getSystem().getConfiguration().locale

        return when(locale.language) {
            "ru" -> about.ru
            "uk" -> about.ua
            else -> about.en
        }
    }
}
