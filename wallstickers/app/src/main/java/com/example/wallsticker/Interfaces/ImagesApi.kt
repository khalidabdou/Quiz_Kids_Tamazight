package com.example.wallsticker.Interfaces


import com.example.wallsticker.Model.image
import com.example.wallsticker.Utilities.Const
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ImagesApi {

    @GET("latest")
    fun getImages(
        @Query("offset") offset: Int,
        @Query("id") id: Int? = null
    ): Call<List<image>>

    companion object {
        operator fun invoke(): ImagesApi {
            return Retrofit.Builder()
                .baseUrl(Const.apiurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ImagesApi::class.java)
        }
    }
}