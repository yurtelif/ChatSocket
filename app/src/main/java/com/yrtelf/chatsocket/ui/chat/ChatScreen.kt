package com.yrtelf.chatsocket.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yrtelf.chatsocket.ui.component.StepScreen
import com.yrtelf.chatsocket.ui.component.Toolbar
import com.yrtelf.chatsocket.ui.theme.N11Purple

@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel()) {

	val systemUiController = rememberSystemUiController()
	val steps by viewModel.currentStep.collectAsState()
	val listState = rememberLazyListState()

	LaunchedEffect(steps.size) {
		systemUiController.setStatusBarColor(color = N11Purple)
		if (steps.isNotEmpty()) {
			listState.animateScrollToItem(steps.lastIndex)
		}
	}


	Scaffold(
		topBar = {
			Toolbar("Asistan11")
		},
		content = { paddingValues ->
			Column(modifier = Modifier.padding(paddingValues)) {
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
		}
	)


}
