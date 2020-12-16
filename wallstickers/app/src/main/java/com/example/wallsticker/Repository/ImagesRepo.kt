package com.example.wallsticker.Repository

import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.Model.quote
import com.example.wallsticker.Retrofit.RetrofitInstance
import retrofit2.Response

class ImagesRepo {

    //this for categories Images
    suspend fun getImagescategories(): Response<List<category>> {
        return  RetrofitInstance.apiImage.getImgesCategories()
    }


    //this for images
    suspend fun getImages(offset:Int,id:Int?): Response<List<image>> {
        return  RetrofitInstance.apiImage.getImages(offset,id)
    }


}