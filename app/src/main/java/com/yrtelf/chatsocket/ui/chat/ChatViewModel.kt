package com.yrtelf.chatsocket.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yrtelf.chatsocket.data.WebSocketManager
import com.yrtelf.chatsocket.domain.SendAndProcessMessageUseCase
import com.yrtelf.chatsocket.domain.LoadJsonDataUseCase
import com.yrtelf.chatsocket.domain.SocketConnectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
	private val loadJsonDataUseCase: LoadJsonDataUseCase,
	private val socketConnectionUseCase: SocketConnectionUseCase,
	private val sendAndProcessMessageUseCase: SendAndProcessMessageUseCase,
) : ViewModel() {

	private val _currentStep = MutableStateFlow<List<Step>>(emptyList())
	val currentStep = _currentStep.asStateFlow()

	init {
		viewModelScope.launch {
			loadJsonDataUseCase()
		}
		socketConnectionUseCase(SocketConnectionUseCase.ConnectionAction.CONNECT)
		observeWebSocketMessages()
		sendMessage(INITIAL_STEP)
	}

	private fun observeWebSocketMessages() {
		viewModelScope.launch {
			sendAndProcessMessageUseCase.observeMessages().collect { step ->
				step?.let {
					addStep(it)
				}
			}
		}
	}

	private fun sendMessage(message: String) {
		viewModelScope.launch {
			sendAndProcessMessageUseCase(message)
		}
	}

	private fun addStep(step: Step) {
		_currentStep.update { it + step }
	}

	override fun onCleared() {
		super.onCleared()
		socketConnectionUseCase(SocketConnectionUseCase.ConnectionAction.DISCONNECT)
	}

	fun onAction(btnInfo: Button) {
		if (btnInfo.action != END_CONVERSATION && isSocketConnected()) {
			addStep(Step(step = btnInfo.label, isSender = true, type = StepType.ANSWER))
			sendMessage(btnInfo.action)
		} else {
			if (isSocketConnected()){
				addStep(Step(step = "", isSender = true, type = StepType.END_CONVERSATION))
				socketConnectionUseCase(SocketConnectionUseCase.ConnectionAction.DISCONNECT)
			}
		}
	}

	private fun isSocketConnected() =
		socketConnectionUseCase.observeConnectionState().value == WebSocketManager.WebSocketState.CONNECTED

	companion object ChatConstants {
		const val INITIAL_STEP = "step_1"
		const val END_CONVERSATION = "end_conversation"
	}
}
