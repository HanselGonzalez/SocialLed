package com.sign.led.di

import android.content.Context
import androidx.room.Room
import com.sign.led.data.Database.SignsDatabase
import com.sign.led.data.Database.dao.ItemViewFullDao
import com.sign.led.data.RepositoryImpl
import com.sign.led.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object RoomModule {

    private const val SIGNS_DATABASE_NAME = "signs_database"


    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context:Context) =
        Room.databaseBuilder(context, SignsDatabase::class.java, SIGNS_DATABASE_NAME)
            .build()


    @Singleton
    @Provides
    fun provideItemViewFullDao(db:SignsDatabase) = db.getItemViewFullDao()



    @Provides
    fun provideRepository(itemViewFullDao:ItemViewFullDao):Repository{
        return RepositoryImpl(itemViewFullDao)
    }





}