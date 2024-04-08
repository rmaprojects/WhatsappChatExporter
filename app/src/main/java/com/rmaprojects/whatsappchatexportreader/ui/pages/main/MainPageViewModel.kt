package com.rmaprojects.whatsappchatexportreader.ui.pages.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.rmaprojects.whatsappchatexportreader.domain.model.Chat
import com.rmaprojects.whatsappchatexportreader.domain.usecase.ChatExportReaderUsecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val useCases: ChatExportReaderUsecases
) : ViewModel() {

    private val _rawText = mutableStateOf<String?>(null)
    val rawText: State<String?> = _rawText

    private val _savingChatState = MutableStateFlow<RequestState<List<Chat>>>(RequestState.Idle)
    val savingChatState = _savingChatState
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500),
            RequestState.Idle
        )

    val chats = useCases.groupChatDateUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500),
            emptyList()
        )

    fun setRawText(newText: String) {
        _rawText.value = newText
    }

    fun saveText() {
        viewModelScope.launch {
            _savingChatState.emit(RequestState.Loading)
            val chatList = useCases.textSplitterUseCase(_rawText.value ?: return@launch)
            _savingChatState.emit(RequestState.Success(chatList))
            _rawText.value = null
        }
    }

    fun deleteAllChats() {
        viewModelScope.launch {
            useCases.deleteAllTextUseCase()
        }
    }


}