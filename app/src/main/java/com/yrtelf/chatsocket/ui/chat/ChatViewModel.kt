package com.yrtelf.chatsocket.ui.chat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yrtelf.chatsocket.data.WebSocketManager
import com.yrtelf.chatsocket.data.local.AppDatabase
import com.yrtelf.chatsocket.data.util.JsonToRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
	private val repository: JsonToRoomRepository,
	private val webSocketManager: WebSocketManager,
	private val database: AppDatabase,
	@ApplicationContext private val context: Context,
) : ViewModel() {

	private val _chatState = MutableStateFlow<ChatUiState?>(null)
	val chatState = _chatState.asStateFlow()

	private val _messageState = MutableStateFlow<String>("")
	val messageState = _messageState.asStateFlow()

	init {
		loadJsonData()
		/**
		 *
		webSocketManager.connect(object : WebSocketListener() {
			override fun onMessage(webSocket: WebSocket, text: String) {
				viewModelScope.launch {
					_messageState.value = text
				}
			}

			override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
				viewModelScope.launch {
					_messageState.value = "Connection failed: ${t.message}"
				}
			}
		})
		*/
	}

	private fun loadJsonData() {
		viewModelScope.launch {
			repository.loadJsonIntoDatabase(context)
			loadStep("step_1")
		}
	}

	private fun loadStep(step: String) {
		viewModelScope.launch {
			val stepData = database.stepDao().getStep(step)
			_chatState.value = ChatUiState(stepData?.step ?: "")
		}
	}


	fun sendMessage(message: String) {
		webSocketManager.sendMessage(message)
	}

	override fun onCleared() {
		super.onCleared()
		webSocketManager.close()
	}
}
