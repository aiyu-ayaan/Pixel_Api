package com.aatec.noticeapp.data.Network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NoticeNetworkEntity : Parcelable {
    var title: String = ""
    var body: String = ""
    var date: String = ""
    var link: String = ""
}