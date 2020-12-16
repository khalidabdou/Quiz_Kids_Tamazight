package com.example.wallsticker.Interfaces


import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.quote
import com.example.wallsticker.Utilities.Const
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


//get quotes latest
interface QuotesApi {
    @GET("QuotesCats")
    suspend fun getQuotesCategories(): Response<List<category>>

    @GET("quotes")
    suspend fun getQuotes(
        @Query("offset") offset: Int,
        @Query("id") id: Int? = null
    ): Response<List<quote>>

}




