package com.example.wallsticker.Utilities

import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.Model.quote

class Const {

    companion object {
        var apiQuotes = "https://api.mocki.io/"
        var DELAY_SET_WALLPAPER: Long = 2000
        val directoryUpload: String = com.example.wallsticker.Config.BASE_URL + "upload/"

        var ImagesTemp = arrayListOf<image>()
        var ImagesByCatTemp = arrayListOf<image>()
        var ImageTempFav=arrayListOf<image>()

        var CatImages = arrayListOf<category>()

        var QuotesTemp = arrayListOf<Any>()
        var QuotesTempFav = arrayListOf<quote>()
        var isFavChanged=true
        var arrayOf: String = "latest"
        var quotes = ArrayList<quote>()
        const val enable_share_with_package: Boolean = true



    }


}