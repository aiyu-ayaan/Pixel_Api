package com.aatec.noticeapp.data.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notice")
data class NoticeCacheEntity(
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val body: String,
    val date: String,
    val link: String
)