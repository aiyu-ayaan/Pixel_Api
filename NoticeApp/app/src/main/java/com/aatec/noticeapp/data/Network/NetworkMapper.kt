package com.aatec.noticeapp.data.Network

import com.aatec.noticeapp.data.Notice
import com.aatec.noticeapp.utils.EntityMapper
import javax.inject.Inject

class NetworkMapper @Inject constructor() : EntityMapper<NoticeNetworkEntity, Notice> {
    override fun mapFromEntity(entity: NoticeNetworkEntity): Notice =
        Notice(entity.title, entity.body, entity.date, entity.link)


    override fun mapToEntity(domainModel: Notice): NoticeNetworkEntity =
        NoticeNetworkEntity().also {
            it.body = domainModel.body
            it.title = domainModel.title
            it.date = domainModel.date
            it.link = domainModel.link
        }


    fun mapFromEntityList(entities: List<NoticeNetworkEntity>): List<Notice> =
        entities.map {
            mapFromEntity(it)
        }


}