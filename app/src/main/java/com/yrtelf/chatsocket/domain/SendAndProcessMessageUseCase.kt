package com.yrtelf.chatsocket.domain

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.yrtelf.chatsocket.data.WebSocketManager
import com.yrtelf.chatsocket.data.local.AppDatabase
import com.yrtelf.chatsocket.domain.mapper.StepMapper
import com.yrtelf.chatsocket.ui.chat.Step
import kotlinx.coroutines.withContext

@Singleton
class SendAndProcessMessageUseCase @Inject constructor(
	private val webSocketManager: WebSocketManager,
	private val gson: Gson,
	private val database: AppDatabase,
	private val stepMapper: StepMapper
) {

	suspend operator fun invoke(step: String) {
		 loadStep(step)
	}

	fun observeMessages(): Flow<Step?> {
		return webSocketManager.incomingMessages
			.map { processIncomingMessage(it) }
			.flowOn(Dispatchers.IO)
	}

	private fun sendMessage(json: String) {
		webSocketManager.sendMessage(json)
	}

	private suspend fun loadStep(step: String) {
		withContext(Dispatchers.IO) {
			database.stepDao().getStep(step)?.let { stepData ->
				sendMessage(gson.toJson(stepData))
			}
		}
	}

	private fun processIncomingMessage(text: String): Step? {
		return try {
			stepMapper.mapJsonToStep(text)
		} catch (e: JsonSyntaxException) {
			Log.e("WebSocketParse", "processIncomingMessage: invalid response format", )
			null
		}
	}
}
