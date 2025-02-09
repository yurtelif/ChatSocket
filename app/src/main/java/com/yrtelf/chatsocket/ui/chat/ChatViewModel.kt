package com.yrtelf.chatsocket.ui.chat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.yrtelf.chatsocket.data.WebSocketManager
import com.yrtelf.chatsocket.data.local.AppDatabase
import com.yrtelf.chatsocket.data.util.JsonToRoomRepository
import com.yrtelf.chatsocket.domain.ProcessWebSocketMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
	private val repository: JsonToRoomRepository,
	private val webSocketManager: WebSocketManager,
	private val database: AppDatabase,
	private val gson: Gson,
	private val processWebSocketMessageUseCase: ProcessWebSocketMessageUseCase,
	@ApplicationContext private val context: Context,
) : ViewModel() {

	private val _currentStep = MutableStateFlow<List<Step>>(emptyList())
	val currentStep = _currentStep.asStateFlow()

	init {
		loadJsonData()
		webSocketManager.connect(object : WebSocketListener() {
			override fun onMessage(webSocket: WebSocket, text: String) {
				viewModelScope.launch {
					processWebSocketMessageUseCase.processMessage(text)
						.collect { step ->
							addStep(step)
						}
				}
			}

			override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
				viewModelScope.launch {
					addStep(Step(step = "Connection failed: ${t.message}"))
				}
			}
		})
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
			val jsonMessage = gson.toJson(stepData)
			sendMessage(jsonMessage)
		}
	}


	private fun sendMessage(message: String) {
		webSocketManager.sendMessage(message)
	}

	override fun onCleared() {
		super.onCleared()
		webSocketManager.close()
	}

	fun onAction(btnInfo: Button) {
		if (btnInfo.action != "end_conversation") {
			addStep(Step(step = btnInfo.label, isSender = true, type = StepType.ANSWER))
			loadStep(btnInfo.action)
		}
	}
	fun addStep(step: Step) {
		_currentStep.update { currentList ->
			currentList.plus(step) // Append new step to the list
		}
	}

}
