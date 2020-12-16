package com.example.wallsticker.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.quote
import com.example.wallsticker.Repository.QuotesRepo
import kotlinx.coroutines.launch
import retrofit2.Response

class QuotesViewModel(private val quotesRepo: QuotesRepo) :ViewModel() {

    var quotesCategories:MutableLiveData<Response<List<category>>> = MutableLiveData()
    var latestquotes:MutableLiveData<Response<List<quote>>> = MutableLiveData()

    fun getQuotesCategories(){
        viewModelScope.launch {
            val categoreisRespo=quotesRepo.getQuotescategories()
            quotesCategories.value=categoreisRespo
        }
    }

    fun getLatestQuotes(offset:Int,id:Int?){
        viewModelScope.launch {
            val quotes=quotesRepo.getQuotes(offset, id)
            latestquotes.value=quotes
        }
    }


}