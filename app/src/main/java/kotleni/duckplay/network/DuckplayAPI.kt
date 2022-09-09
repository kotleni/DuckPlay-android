package kotleni.duckplay.network

import kotleni.duckplay.entities.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object DuckplayAPI {
    val baseUrl = "https://kotleni.github.io/"

    interface APIService {
        @GET("/duckplay/games.json")
        fun getGames(): Call<List<Game>>

        @GET("/duckplay/info.json")
        fun getInfo(): Call<Info>

        @GET("/duckplay/games/{id}/game.json")
        fun getGameInfo(@Path("id") id: String): Call<GameInfo>
    }

    private fun getRetrofit(): Retrofit {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client : OkHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit
    }

    fun getService(): APIService {
        return getRetrofit().create(APIService::class.java)
    }
}