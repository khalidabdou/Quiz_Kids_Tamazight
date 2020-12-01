package com.example.wallsticker.Interfaces

import com.example.wallsticker.Utilities.Const
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PUT
import retrofit2.http.Path

interface IncrementServiceQuote {

    @FormUrlEncoded
    @PUT("incShareQuote/{id}")
    fun incrementShare(
        @Path("id") id: Int?,
        @Field("count_shared") count_shared: Int?,
    )
            : Call<Any>

    companion object {
        operator fun invoke(): IncrementServiceQuote {
            return Retrofit.Builder()
                .baseUrl(Const.apiurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IncrementServiceQuote::class.java)
        }
    }
}

interface IncrementViewQuote {

    @FormUrlEncoded
    @PUT("incViewQuote/{id}")
    fun incrementViews(
        @Path("id") id: Int?,
        @Field("count_views") count_views: Int?,
    )
            : Call<Any>

    companion object {
        operator fun invoke(): IncrementViewQuote {
            return Retrofit.Builder()
                .baseUrl(Const.apiurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IncrementViewQuote::class.java)
        }
    }
}