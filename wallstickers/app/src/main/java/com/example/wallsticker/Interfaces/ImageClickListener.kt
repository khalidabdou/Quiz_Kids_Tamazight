package com.example.wallsticker.Interfaces

import android.view.View
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image

interface ImageClickListener {

    fun onImageClicked(view: View, image: image, pos: Int)
    fun onCatClicked(view: View, category: category, pos: Int)

}