package com.rmaprojects.whatsappchatexportreader.domain.usecase

import android.util.Log
import com.rmaprojects.whatsappchatexportreader.domain.model.GroupedChat
import com.rmaprojects.whatsappchatexportreader.domain.repository.ChatExportReaderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GroupChatDateUseCase(
    private val repository: ChatExportReaderRepository
) {
    operator fun invoke(): Flow<List<GroupedChat>> {
        return repository.getAllSavedChat().map { chatList ->
            val mappedGroupChat = chatList.groupBy {
                it.date
            }

            mappedGroupChat.map { (chatDate, chatList) ->
                GroupedChat(chatDate, chatList)
            }
        }
    }
}