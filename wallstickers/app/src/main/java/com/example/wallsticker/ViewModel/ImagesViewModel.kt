package com.example.wallsticker.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.Repository.ImagesRepo
import kotlinx.coroutines.launch
import retrofit2.Response

class ImagesViewModel(private val imageRepo: ImagesRepo):ViewModel() {

    var imagesCategories: MutableLiveData<Response<List<category>>> = MutableLiveData()
    var images: MutableLiveData<Response<List<image>>> = MutableLiveData()

    fun getImagesCategories(){
        viewModelScope.launch {
            val categoreisRespo=imageRepo.getImagescategories()
            imagesCategories.value=categoreisRespo
        }
    }


    fun getImages(offset:Int,id:Int?){
        viewModelScope.launch {
            val imagesRespo=imageRepo.getImages(offset,id)
            images.value=imagesRespo
        }
    }




}