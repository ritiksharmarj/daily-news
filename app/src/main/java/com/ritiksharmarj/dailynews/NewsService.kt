package com.ritiksharmarj.dailynews

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// https://newsapi.org/v2/everything?q=bitcoin&apiKey=API_KEY
// https://newsapi.org/v2/top-headlines?country=in&apiKey=API_KEY

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "4272179dd10943fc88431c4bd18cbb93"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country: String, @Query("page") page: Int): Call<News>

    // https://newsapi.org/v2/top-headlines?apiKey=4272179dd10943fc88431c4bd18cbb93&country=in&page=1
}

// Singleton object for Retrofit
object NewsService {
    // To implement NewsInterface, create reference of it
    val newsInstance: NewsInterface

    init {
        // Retrofit object
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}