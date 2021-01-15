package com.example.wallsticker.Utilities

import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.Model.quote

class Const {

    companion object {

        var apiurl = "https://usaapi.dev3pro.co/api/"
        var DELAY_SET_WALLPAPER: Long = 2000
        var COUNTER_AD_SHOW = 3
        var INCREMENT_COUNTER = 0
        val directoryUpload: String = com.example.wallsticker.Config.BASE_URL + "upload/images/"
        val directoryUploadCat: String =
            com.example.wallsticker.Config.BASE_URL + "upload/category/"


        //for images
        var ImagesTemp = arrayListOf<image>()
        var ImagesByCatTemp = arrayListOf<image>()
        var ImageTempFav = arrayListOf<image>()

        var CatImages = arrayListOf<category>()


        var isFavChanged = true
        var arrayOf: String = "latest"

        const val enable_share_with_package: Boolean = true


        //for quotes
        var quotesarrayof: String = "latest"
        var quotes = ArrayList<quote>()
        var QuotesTemp = arrayListOf<Any>()
        var QuotesTempFav = arrayListOf<Any>()
        var QuotesByCat = arrayListOf<Any>()
        var QuotesCategories = arrayListOf<category>()

        //Room database
        const val DATABASE_NAME="MY_DB"
        const val TABLE_IMAGE="tbl_images"
    }


}