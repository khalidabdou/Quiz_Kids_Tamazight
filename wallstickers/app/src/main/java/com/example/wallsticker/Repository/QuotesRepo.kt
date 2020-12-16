package com.example.wallsticker.Repository

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallsticker.Interfaces.QuotesApi
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.quote
import com.example.wallsticker.Retrofit.RetrofitInstance
import retrofit2.Call

import retrofit2.Response

class QuotesRepo()  {

    //this for categories quotes
    suspend fun getQuotescategories(): Response<List<category>> {
        return  RetrofitInstance.apiQuotes.getQuotesCategories()
    }

    suspend fun getQuotes(offset:Int,id :Int?):Response<List<quote>>{
        return RetrofitInstance.apiQuotes.getQuotes(offset, id)
    }


}