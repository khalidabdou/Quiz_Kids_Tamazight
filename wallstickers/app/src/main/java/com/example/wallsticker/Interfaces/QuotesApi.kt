package com.example.wallsticker.Interfaces


import com.example.wallsticker.Model.quote
import com.example.wallsticker.Utilities.Const
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


//get quotes latest
interface QuotesApi {
    @GET("quotes")
    fun getQuotes(
        @Query("offset") offset: Int,
        @Query("id") id: Int? = null
    ): Call<List<quote>>

    companion object {
        operator fun invoke(): QuotesApi {
            return Retrofit.Builder()
                .baseUrl(Const.apiurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QuotesApi::class.java)
        }
    }
}


//get quotes by cayrgory
public interface QuotesApiByCat {


    @GET("quotesbycat")
    fun getQuotes(
        @Query("offset") offset: Int,
        @Query("cid") id: Int? = null
    ): Call<List<quote>>

    companion object {
        operator fun invoke(): QuotesApiByCat {
            return Retrofit.Builder()
                .baseUrl(Const.apiurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QuotesApiByCat::class.java)
        }

    }

    @FormUrlEncoded
    @PUT("incShareQuote/{id}")
    fun incrementShare(
        @Path("id") id: Int,
        @Field("count_shared") count_shared: String
    ): Call<quote>


}



