package com.rmaprojects.whatsappchatexportreader.data.source.local

import com.rmaprojects.whatsappchatexportreader.data.source.local.database.RecentChatDao
import com.rmaprojects.whatsappchatexportreader.data.source.local.entity.LoadedChat
import com.rmaprojects.whatsappchatexportreader.domain.model.Chat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: RecentChatDao
) {

    suspend fun upsertChatIntoDatabase(chatList: List<LoadedChat>) {
        dao.upsertAll(chatList)
    }

    suspend fun deleteAllChat() {
        dao.deleteAllLoadedChat()
    }

    fun getAllSavedChat(): Flow<List<LoadedChat>> {
        return dao.getAllChat()
    }
    
}