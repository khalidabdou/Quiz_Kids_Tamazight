package com.example.wallsticker.di

import android.content.Context
import androidx.room.Room
import com.example.wallsticker.Utilities.Const.Companion.DATABASE_NAME
import com.example.wallsticker.data.databsae.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context)= Room.databaseBuilder(
        context,
        Database::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: Database)=database.imageDao()
}