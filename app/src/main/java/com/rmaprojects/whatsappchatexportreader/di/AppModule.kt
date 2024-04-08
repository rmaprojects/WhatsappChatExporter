package com.rmaprojects.whatsappchatexportreader.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rmaprojects.whatsappchatexportreader.data.repository.ChatExportReaderRepositoryImpl
import com.rmaprojects.whatsappchatexportreader.data.source.local.LocalDataSource
import com.rmaprojects.whatsappchatexportreader.data.source.local.database.RecentChatDao
import com.rmaprojects.whatsappchatexportreader.data.source.local.database.RecentChatDatabase
import com.rmaprojects.whatsappchatexportreader.domain.repository.ChatExportReaderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: LocalDataSource
    ): ChatExportReaderRepository {
        return ChatExportReaderRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(
       app: Application
    ): RecentChatDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            RecentChatDatabase::class.java,
            "recentchat.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(
        database: RecentChatDatabase
    ): LocalDataSource {
        return LocalDataSource(database.dao())
    }
}