package com.rmaprojects.whatsappchatexportreader.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rmaprojects.whatsappchatexportreader.data.source.local.entity.LoadedChat

@Database(
    entities = [LoadedChat::class],
    version = 1,
    exportSchema = true
)
abstract class RecentChatDatabase: RoomDatabase() {
    abstract fun dao(): RecentChatDao
}