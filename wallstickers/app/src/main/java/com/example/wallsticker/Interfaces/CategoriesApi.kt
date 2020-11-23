package com.example.wallsticker.Interfaces


import com.example.wallsticker.Config
import com.example.wallsticker.Model.category
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val BASE_URL = "http://dev3por.website/GreenApp2/material_wallpaper"

interface CategoriesApi {
    @GET("api/api.php?action=get_category")
    fun getCategories(): Call<List<category>>

    companion object {
        operator fun invoke(): CategoriesApi {
            return Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CategoriesApi::class.java)
        }
    }
}