package com.example.wallsticker.Utilities

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.util.*

class Tools {
    companion object {
        fun getImageUri(inImage: Bitmap, context: Context): Uri? {
            val bytes = ByteArrayOutputStream()
            inImage.compress(Bitmap.CompressFormat.JPEG, 70, bytes)
            val path: String = MediaStore.Images.Media.insertImage(
                context.contentResolver,
                inImage,
                UUID.randomUUID().toString().toString() + ".png",
                "drawing"
            )
            return Uri.parse(path)
        }
    }


}