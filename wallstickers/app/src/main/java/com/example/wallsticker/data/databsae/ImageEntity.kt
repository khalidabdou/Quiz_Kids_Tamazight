package com.example.wallsticker.data.databsae

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wallsticker.Model.image
import com.example.wallsticker.Utilities.Const.Companion.TABLE_IMAGE


@Entity(tableName = TABLE_IMAGE)
class ImageEntity(
    var image: image
) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}