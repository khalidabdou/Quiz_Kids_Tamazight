package com.example.wallsticker.Interfaces


import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.Retrofit.RetrofitInstance
import com.example.wallsticker.Utilities.Const
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface QuoteesCategoriesApi {
    @GET("QuotesCats")
     suspend fun getQuotesCategories(): Response<List<category>>


}


interface ImageesApi {
    @GET("ImgCats")
    suspend fun getImgesCategories(): Response<List<category>>

    @GET("latest")
    suspend fun getImages(
        @Query("offset") offset: Int,
        @Query("id") id: Int? = null
    ): Response<List<image>>
}