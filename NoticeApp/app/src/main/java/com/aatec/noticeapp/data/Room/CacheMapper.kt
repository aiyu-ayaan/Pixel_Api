package com.aatec.noticeapp.data.Room

import com.aatec.noticeapp.data.Notice
import com.aatec.noticeapp.utils.EntityMapper
import javax.inject.Inject

class CacheMapper @Inject constructor() : EntityMapper<NoticeCacheEntity, Notice> {


    override fun mapFromEntity(entity: NoticeCacheEntity): Notice =
         Notice(entity.title, entity.body, entity.date, entity.link)


    override fun mapToEntity(domainModel: Notice): NoticeCacheEntity =
        NoticeCacheEntity(
            domainModel.title,
            domainModel.body,
            domainModel.date,
            domainModel.link
        )


    fun mapFromEntityList(entities: List<NoticeCacheEntity>): List<Notice> =
        entities.map {
            mapFromEntity(it)
        }

}