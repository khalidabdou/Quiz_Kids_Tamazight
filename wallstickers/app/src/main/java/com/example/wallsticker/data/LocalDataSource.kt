package com.example.wallsticker.data

import com.example.wallsticker.data.databsae.ImageDao
import com.example.wallsticker.data.databsae.ImageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val imageDao: ImageDao
) {

     fun readdatabase(): Flow<List<ImageEntity>>{
        return imageDao.readImages()
    }

    suspend fun insertImage(imageEntity: ImageEntity){
        imageDao.insertImage(imageEntity)
    }

}