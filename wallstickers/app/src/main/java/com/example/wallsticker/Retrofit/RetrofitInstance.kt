package com.example.wallsticker.Retrofit

import com.example.wallsticker.Interfaces.ImagesApi
import com.example.wallsticker.Interfaces.QuotesApi
import com.example.wallsticker.Utilities.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Const.apiurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //for quotes cat
    val apiQuotes: QuotesApi by lazy {
        retrofit.create(QuotesApi::class.java)
    }

    //for Images cat
    val apiImage: ImagesApi by lazy {
        retrofit.create(ImagesApi::class.java)
    }


}