package com.rmaprojects.whatsappchatexportreader.util

import com.rmaprojects.whatsappchatexportreader.data.source.local.entity.LoadedChat
import com.rmaprojects.whatsappchatexportreader.domain.model.Chat

fun LoadedChat.convertToModel() = Chat(
    this.text,
    this.time,
    this.name,
    this.date
)

fun Chat.convertToEntity() = LoadedChat(
    this.text,
    this.date,
    this.time,
    this.name
)