package com.rmaprojects.whatsappchatexportreader.domain.usecase

import com.rmaprojects.whatsappchatexportreader.domain.repository.ChatExportReaderRepository

class DeleteAllTextUseCase(private val repository: ChatExportReaderRepository) {

    suspend operator fun invoke() {
        repository.deleteAllChat()
    }

}