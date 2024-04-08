package com.rmaprojects.whatsappchatexportreader.di

import com.rmaprojects.whatsappchatexportreader.domain.repository.ChatExportReaderRepository
import com.rmaprojects.whatsappchatexportreader.domain.usecase.ChatExportReaderUsecases
import com.rmaprojects.whatsappchatexportreader.domain.usecase.DeleteAllTextUseCase
import com.rmaprojects.whatsappchatexportreader.domain.usecase.GroupChatDateUseCase
import com.rmaprojects.whatsappchatexportreader.domain.usecase.TextSplitterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideTextSplitterUseCase(
        repository: ChatExportReaderRepository
    ): ChatExportReaderUsecases {
        return ChatExportReaderUsecases(
            textSplitterUseCase = TextSplitterUseCase(repository),
            deleteAllTextUseCase = DeleteAllTextUseCase(repository),
            groupChatDateUseCase = GroupChatDateUseCase(repository)
        )
    }
}