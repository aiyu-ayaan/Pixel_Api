package com.aatec.noticeapp.data

import com.aatec.noticeapp.data.Network.NetworkMapper
import com.aatec.noticeapp.data.Network.NoticeNetworkEntity
import com.aatec.noticeapp.data.Room.CacheMapper
import com.aatec.noticeapp.data.Room.NoticeDao
import com.aatec.noticeapp.utils.DataState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NoticeRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val noticeDao: NoticeDao,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    @ExperimentalCoroutinesApi
    suspend fun getNotice(): Flow<DataState<List<Notice>>> = channelFlow {
        val ref = db.collection("Notice_Old").orderBy("created", Query.Direction.DESCENDING)
        try {
            ref.addSnapshotListener { value, error ->
                runBlocking {
                    if (error != null) {
                        send(DataState.Error(error))
                    } else {
                        if (value != null) {
                            send(DataState.Loading)
                            val networkNotice = value.toObjects(NoticeNetworkEntity::class.java)
                            val notices = networkMapper.mapFromEntityList(networkNotice)
                            noticeDao.deleteAll()
                            for (notice in notices) {
                                noticeDao.insert(cacheMapper.mapToEntity(notice))
                            }
                            val cachedBlog = noticeDao.get()
                            send(DataState.Success(cacheMapper.mapFromEntityList(cachedBlog)))
                        }
                    }
                }
            }
            awaitClose()
        } catch (e: Exception) {
            send(DataState.Error(e))
            awaitClose()
        }
    }.flowOn(Dispatchers.Main)
}