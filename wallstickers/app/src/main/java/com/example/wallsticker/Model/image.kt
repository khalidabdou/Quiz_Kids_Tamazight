package com.example.wallsticker.Model

import kotlinx.android.parcel.Parcelize

@Parcelize
class image(
    val image_id: Int?,
    val image_upload: String?,
    val view_count: Int,
    val download_count: Int,
    var isfav: Int?

)