package com.rmaprojects.whatsappchatexportreader.domain.repository

import com.rmaprojects.whatsappchatexportreader.data.source.local.entity.LoadedChat
import com.rmaprojects.whatsappchatexportreader.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChatExportReaderRepository {
    suspend fun upsertChatIntoDatabase(chatList: List<Chat>)
    suspend fun deleteAllChat()
    fun getAllSavedChat(): Flow<List<Chat>>
}