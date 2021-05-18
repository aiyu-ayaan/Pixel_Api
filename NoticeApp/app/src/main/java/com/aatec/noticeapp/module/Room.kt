package com.aatec.noticeapp.module

import android.content.Context
import androidx.room.Room
import com.aatec.noticeapp.data.Room.NoticeDao
import com.aatec.noticeapp.data.Room.NoticeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Room {


    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): NoticeDatabase =
        Room.databaseBuilder(
            context,
            NoticeDatabase::class.java,
            NoticeDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun getDao(noticeDatabase: NoticeDatabase): NoticeDao =
        noticeDatabase.noticeDao()
}