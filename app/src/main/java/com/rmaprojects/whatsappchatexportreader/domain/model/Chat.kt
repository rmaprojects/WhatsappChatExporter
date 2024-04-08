package com.rmaprojects.whatsappchatexportreader.domain.model

data class Chat(
    val text: String,
    val time: String,
    val name: String,
    val date: String = ""
)
