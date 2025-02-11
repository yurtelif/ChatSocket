package com.yrtelf.chatsocket.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yrtelf.chatsocket.ui.component.StepScreen

@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel()) {

	val steps by viewModel.currentStep.collectAsState()
	val listState = rememberLazyListState()

	LaunchedEffect(steps.size) {
		if (steps.isNotEmpty()) {
			listState.animateScrollToItem(steps.lastIndex)
		}
	}

	LazyColumn(
		state = listState,
		modifier = Modifier.fillMaxSize()
			.background(Color.White),
		contentPadding = PaddingValues(8.dp)
	) {
		items(steps) { step ->
			StepScreen(step, viewModel::onAction)
		}
	}
}
