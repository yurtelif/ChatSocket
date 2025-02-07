package com.yrtelf.chatsocket.ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun WebSocketScreen(viewModel: ChatViewModel = hiltViewModel()) {
	val message by viewModel.chatState.collectAsState()
	var inputText by remember { mutableStateOf(TextFieldValue("")) }

	Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
		Text(text = "WebSocket Echo Chat", style = MaterialTheme.typography.bodyMedium)

		OutlinedTextField(
			value = inputText,
			onValueChange = { inputText = it },
			label = { Text("Message") },
			modifier = Modifier.fillMaxWidth()
		)

		Button(
			onClick = { viewModel.sendMessage(inputText.text) },
			modifier = Modifier.padding(top = 8.dp)
		) {
			Text("Send Message")
		}

		Spacer(modifier = Modifier.height(16.dp))

		Text(text = "Response: ${message?.text}", style = MaterialTheme.typography.bodySmall)
	}
}
