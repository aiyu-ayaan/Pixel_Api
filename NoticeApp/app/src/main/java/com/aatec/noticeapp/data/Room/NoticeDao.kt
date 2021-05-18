package com.aatec.noticeapp.data.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoticeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noticeCacheEntity: NoticeCacheEntity)

    @Query("SELECT * FROM notice")
    suspend fun get(): List<NoticeCacheEntity>

    @Query("DELETE FROM notice")
    suspend fun deleteAll()
}