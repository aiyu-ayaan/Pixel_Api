package com.aatec.noticeapp.data.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoticeCacheEntity::class], version = 2)
abstract class NoticeDatabase : RoomDatabase() {

    abstract fun noticeDao(): NoticeDao

    companion object {
        const val DATABASE_NAME = "blog_db"
    }
}