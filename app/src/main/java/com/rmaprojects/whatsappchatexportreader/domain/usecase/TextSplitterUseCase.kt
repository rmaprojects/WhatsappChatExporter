package com.rmaprojects.whatsappchatexportreader.domain.usecase

import com.rmaprojects.whatsappchatexportreader.domain.model.Chat
import com.rmaprojects.whatsappchatexportreader.domain.repository.ChatExportReaderRepository

class TextSplitterUseCase(
    val repository: ChatExportReaderRepository
) {

    suspend operator fun invoke(rawText: String): List<Chat> {

        val textList = rawText.split("\n")
        val chatList = mutableListOf<Chat>()
        textList.forEach { chat ->
            val splitedTextAndTime = chat.split(" - ")
            val splitedNameAndText = splitedTextAndTime.last().split(": ")
            val name = splitedNameAndText.first()
            val timeStamp = splitedTextAndTime.first()
            val chatText = splitedNameAndText.last()
            val date = timeStamp.split(", ").first()
            val time = timeStamp.split(", ").last()

            chatList.add(
                Chat(
                    chatText,
                    time,
                    name,
                    date
                )
            )
        }


        repository.upsertChatIntoDatabase(chatList)

        return chatList
    }

}