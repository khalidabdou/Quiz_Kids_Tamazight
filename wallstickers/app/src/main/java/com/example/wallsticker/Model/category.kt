package com.example.wallsticker.Model

import com.google.gson.annotations.SerializedName

data class category(

    @SerializedName("id", alternate = ["cid"])
    val id: Int,
    @SerializedName("category_name", alternate = ["name"])
    val name: String,
    @SerializedName("category_image", alternate = ["image"])
    val image: String,

    val total_wallpaper: String?

)