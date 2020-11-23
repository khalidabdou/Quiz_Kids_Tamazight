package com.example.wallsticker.Interfaces


import com.example.wallsticker.Config
import com.example.wallsticker.Model.quote
import com.example.wallsticker.Utilities.Const
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface QuotesApi {

    @GET("v1/a6da0825/")
    fun getQuotes(): Call<List<quote>>

    companion object {
        operator fun invoke(): QuotesApi {
            return Retrofit.Builder()
                .baseUrl(Const.apiQuotes)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QuotesApi::class.java)
        }
    }
}