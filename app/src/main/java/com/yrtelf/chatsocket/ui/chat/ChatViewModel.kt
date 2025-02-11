package com.yrtelf.chatsocket.ui.chat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yrtelf.chatsocket.domain.ChatWebSocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
	private val chatWebSocketUseCase: ChatWebSocketUseCase,
	@ApplicationContext private val context: Context
) : ViewModel() {

	private val _currentStep = MutableStateFlow<List<Step>>(emptyList())
	val currentStep = _currentStep.asStateFlow()

	init {
		viewModelScope.launch {
			chatWebSocketUseCase.loadJsonData(context)
			chatWebSocketUseCase.connect()
			chatWebSocketUseCase.loadStep(INITIAL_STEP)
		}
		observeWebSocketMessages()

	}

	private fun observeWebSocketMessages() {
		viewModelScope.launch {
			chatWebSocketUseCase.observeMessages().collect { step ->
				addStep(step)
			}
		}
	}

	private fun sendMessage(message: String) {
		viewModelScope.launch {
			chatWebSocketUseCase.loadStep(message)
		}
	}

	private fun addStep(step: Step) {
		_currentStep.update { it + step }
	}

	override fun onCleared() {
		super.onCleared()
		chatWebSocketUseCase.disconnect()
	}

	fun onAction(btnInfo: Button) {
		if (btnInfo.action != END_CONVERSATION) {
			addStep(Step(step = btnInfo.label, isSender = true, type = StepType.ANSWER))
			sendMessage(btnInfo.action)
		} else {
			chatWebSocketUseCase.disconnect()
			// TODO: ui güncelle bittiğine dair
		}
	}

	companion object ChatConstants {
		const val INITIAL_STEP = "step_1"
		const val END_CONVERSATION = "end_conversation"
	}
}
