package com.rmaprojects.whatsappchatexportreader.ui.pages.main


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rmaprojects.apirequeststate.RequestState
import com.rmaprojects.whatsappchatexportreader.ui.pages.main.components.ChatBubbles
import com.rmaprojects.whatsappchatexportreader.ui.pages.main.components.EmptyContent
import com.rmaprojects.whatsappchatexportreader.ui.pages.main.components.StickyDateHeader
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainPage(
    viewModel: MainPageViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val chatList = viewModel.chats.collectAsStateWithLifecycle()
    val saveChatState = viewModel.savingChatState.collectAsStateWithLifecycle()
    val rawText = remember {
        viewModel.rawText
    }
    var dialogState by remember {
        mutableStateOf(DialogState())
    }
    val lazyColumnState = rememberLazyListState()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
            it?.let {
                val file = createTempFile()
                it.let { context.contentResolver.openInputStream(it) }.use { input ->
                    file.outputStream().use { output ->
                        input?.copyTo(output)
                    }
                }
                viewModel.setRawText(file.readText())
                file.delete()
            }
        }

    if (rawText.value != null) {
        viewModel.saveText()
    }

    Scaffold(
        floatingActionButton = {
            if (chatList.value.isNotEmpty()) {
                FloatingActionButton(onClick = {
                    scope.launch {
                        lazyColumnState.scrollBy(10000F)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Kebawah"
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Chat History Reader")
                },
                actions = {
                    if (chatList.value.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                dialogState = DialogState(
                                    isShown = true,
                                    titleText = "Yakin pengen ganti .txt yang lain?",
                                    contentText = "Pastikan udah ada backup-an nya yang barusan",
                                    onConfirm = {
                                        launcher.launch(arrayOf("text/plain"))
                                        dialogState.onDismiss()
                                    },
                                    onDismiss = {
                                        dialogState = DialogState(isShown = false)
                                    }
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "New .txt"
                            )
                        }
                        IconButton(
                            onClick = {
                                dialogState = DialogState(
                                    isShown = true,
                                    titleText = "Yakin pengen ganti ngehapus?",
                                    contentText = "Pastikan udah ada backup-an nya yang barusan",
                                    onConfirm = {
                                        viewModel.deleteAllChats()
                                        dialogState.onDismiss()
                                    },
                                    onDismiss = {
                                        dialogState = DialogState(isShown = false)
                                    }
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete all data"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (saveChatState.value is RequestState.Loading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Tunggu sebentar...")
                Spacer(modifier = Modifier.height(8.dp))
                CircularProgressIndicator()
            }
        } else {
            if (chatList.value.isEmpty()) {
                EmptyContent(
                    modifier = Modifier.padding(innerPadding),
                    onButtonClick = {
                        launcher.launch(arrayOf("text/plain"))
                    }
                )
            } else {
                LazyColumn(
                    state = lazyColumnState,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {

                    chatList.value.forEach { groupedChat ->
                        stickyHeader {
                            StickyDateHeader(date = groupedChat.date)
                        }
                        items(groupedChat.chatList) { chats ->
                            ChatBubbles(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = chats.text,
                                name = chats.name,
                                time = chats.time
                            )
                        }
                    }
                }
            }
        }

        if (dialogState.isShown) {
            ConfirmationDialog(
                titleText = dialogState.titleText,
                contentText = dialogState.contentText,
                onConfirm = dialogState.onConfirm,
                onDismiss = dialogState.onDismiss
            )
        }
    }
}

@Composable
private fun ConfirmationDialog(
    titleText: String,
    contentText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Ya")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Nanti dulu deh")
            }
        },
        title = {
            Text(text = titleText)
        },
        text = {
            Text(text = contentText)
        },
    )
}

private data class DialogState(
    val isShown: Boolean = false,
    val titleText: String = "",
    val contentText: String = "",
    val onDismiss: () -> Unit = {},
    val onConfirm: () -> Unit = {}
)