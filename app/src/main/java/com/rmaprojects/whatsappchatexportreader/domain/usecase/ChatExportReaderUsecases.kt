package com.rmaprojects.whatsappchatexportreader.domain.usecase

data class ChatExportReaderUsecases(
    val textSplitterUseCase: TextSplitterUseCase,
    val deleteAllTextUseCase: DeleteAllTextUseCase,
    val groupChatDateUseCase: GroupChatDateUseCase
)
