package com.aatec.noticeapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notice(
    val title: String,
    val body: String,
    val date: String,
    val link: String
) : Parcelable