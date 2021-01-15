package com.example.wallsticker.data.databsae

import androidx.room.TypeConverter
import com.example.wallsticker.Model.image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class imageTypeConverter {

    var gson = Gson()
    @TypeConverter
    fun imageToString(image: image):String{
        return gson.toJson(image)
    }

    @TypeConverter
    fun StringToImage(data : String){
        var listType =object :TypeToken<image>(){}.type
        return gson.fromJson(data,listType)
    }

}