package com.example.wallsticker.Repository

import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.Model.quote
import com.example.wallsticker.Retrofit.RetrofitInstance
import com.example.wallsticker.data.LocalDataSource
import com.example.wallsticker.data.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class ImagesRepo @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
){
    val remot=remoteDataSource
    val local=localDataSource

    //this for categories Images
    suspend fun getImagescategories(): Response<List<category>> {
        return  RetrofitInstance.apiImage.getImgesCategories()
    }


    //this for images
    suspend fun getImages(offset:Int,id:Int?): Response<List<image>> {
        return  RetrofitInstance.apiImage.getImages(offset,id)
    }


}