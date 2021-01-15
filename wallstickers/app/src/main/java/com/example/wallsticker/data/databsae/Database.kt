package com.example.wallsticker.data.databsae

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [ImageEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(imageTypeConverter::class)
abstract class Database ():RoomDatabase(){

    abstract fun imageDao(): ImageDao
}