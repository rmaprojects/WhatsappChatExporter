package com.rmaprojects.whatsappchatexportreader.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoadedChat(
    val text: String,
    val date: String,
    val time: String,
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)