package com.example.wallsticker.Interfaces


import com.example.wallsticker.Model.category
import com.example.wallsticker.Utilities.Const
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface CategoriesApi {


    @GET("QuotesCats")
    fun getCategories(): Call<List<category>>

    companion object {
        operator fun invoke(): CategoriesApi {
            return Retrofit.Builder()
                .baseUrl(Const.apiurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CategoriesApi::class.java)
        }
    }
}


interface CategoriesImageApi {
    @GET("ImgCats")
    fun getCategories(): Call<List<category>>

    companion object {
        operator fun invoke(): CategoriesImageApi {
            return Retrofit.Builder()
                .baseUrl(Const.apiurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CategoriesImageApi::class.java)
        }
    }
}