package com.yrtelf.chatsocket.ui.chat

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yrtelf.chatsocket.ui.component.StepScreen

@Composable
fun WebSocketScreen(viewModel: ChatViewModel = hiltViewModel()) {

	val steps by viewModel.currentStep.collectAsState()

	LazyColumn(
		modifier = Modifier.fillMaxSize(),
		contentPadding = PaddingValues(8.dp)
	) {
		items(steps) { step ->
			StepScreen(step, viewModel::onAction)
		}
	}
}
