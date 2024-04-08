package com.rmaprojects.whatsappchatexportreader.data.repository

import com.rmaprojects.whatsappchatexportreader.data.source.local.LocalDataSource
import com.rmaprojects.whatsappchatexportreader.domain.model.Chat
import com.rmaprojects.whatsappchatexportreader.domain.repository.ChatExportReaderRepository
import com.rmaprojects.whatsappchatexportreader.util.convertToEntity
import com.rmaprojects.whatsappchatexportreader.util.convertToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatExportReaderRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : ChatExportReaderRepository {
    override suspend fun upsertChatIntoDatabase(chatList: List<Chat>) {
        val loadedChat = chatList.map {
            it.convertToEntity()
        }


        localDataSource.upsertChatIntoDatabase(loadedChat)
    }

    override suspend fun deleteAllChat() {
        localDataSource.deleteAllChat()
    }

    override fun getAllSavedChat(): Flow<List<Chat>> {
        return localDataSource.getAllSavedChat().map { loadedChatList ->
            loadedChatList.map { loadedChat ->
                loadedChat.convertToModel()
            }
        }
    }
}