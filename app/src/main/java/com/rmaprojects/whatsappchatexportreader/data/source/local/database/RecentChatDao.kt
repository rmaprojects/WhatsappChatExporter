package com.rmaprojects.whatsappchatexportreader.data.source.local.database

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.rmaprojects.whatsappchatexportreader.data.source.local.entity.LoadedChat
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentChatDao {

    @Transaction
    suspend fun upsertAll(chat: List<LoadedChat>) {
        deleteAllLoadedChat()
        insertLoadedChat(chat)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoadedChat(chat: List<LoadedChat>)

    @Query("DELETE FROM LoadedChat")
    suspend fun deleteAllLoadedChat()

    @Query("SELECT * FROM LoadedChat")
    fun getAllChat(): Flow<List<LoadedChat>>
}