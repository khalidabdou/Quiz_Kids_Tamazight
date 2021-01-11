package com.example.wallsticker.data

import com.example.wallsticker.Interfaces.ImagesApi
import com.example.wallsticker.Model.image
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val imagesApi: ImagesApi
) {

    suspend fun getImages(offset:Int,id:Int?): Response<List<image>> {
        return  imagesApi.getImages(offset,id)
    }
}