package com.rmaprojects.whatsappchatexportreader.ui.pages.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rmaprojects.whatsappchatexportreader.R

@Composable
fun ChatBubbles(
    text: String,
    name: String,
    time: String,
    modifier: Modifier = Modifier,
) {
    if (text != stringResource(R.string.init_text)) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = if (name == "Raka M. A") Alignment.Start else Alignment.End
        ) {
            Card(
                modifier = Modifier
                    .widthIn(max = 320.dp),
                colors = if (name != "Raka M. A") CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
                else CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                shape = if (name == "Raka M. A") RoundedCornerShape(
                    topEnd = 24.dp,
                    topStart = 24.dp,
                    bottomEnd = 24.dp,
                )
                else RoundedCornerShape(
                    topEnd = 24.dp,
                    topStart = 24.dp,
                    bottomStart = 24.dp
                ),
                elevation = if (name == "Raka M. A") CardDefaults.elevatedCardElevation()
                else CardDefaults.cardElevation()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = if (text == "<Media omitted>") "(Stiker/Gambar)" else text,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = time, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }

        }
    } else {
        Box {
            Card(
                modifier = modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xffffdf9d),
                    contentColor = Color(0xff251a00),
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}