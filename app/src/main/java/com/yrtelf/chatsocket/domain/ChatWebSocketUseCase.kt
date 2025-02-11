package com.yrtelf.chatsocket.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton
import com.google.gson.JsonSyntaxException
import android.content.Context
import com.google.gson.Gson
import com.yrtelf.chatsocket.data.WebSocketManager
import com.yrtelf.chatsocket.data.local.AppDatabase
import com.yrtelf.chatsocket.data.util.JsonToRoomRepository
import com.yrtelf.chatsocket.domain.mapper.StepMapper
import com.yrtelf.chatsocket.ui.chat.Step
import com.yrtelf.chatsocket.ui.chat.StepType

@Singleton
class ChatWebSocketUseCase @Inject constructor(
	private val webSocketManager: WebSocketManager,
	private val gson: Gson,
	private val database: AppDatabase,
	private val jsonToRoomRepository: JsonToRoomRepository,
	private val stepMapper: StepMapper
) {

	private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

	suspend fun loadJsonData(context: Context) {
		jsonToRoomRepository.loadJsonIntoDatabase(context)
	}

	fun observeMessages(): Flow<Step> {
		return webSocketManager.incomingMessages
			.map { processIncomingMessage(it) }
			.flowOn(Dispatchers.IO)
	}

	fun connect() {
		webSocketManager.connect()
	}

	private fun sendMessage(json: String) {
		webSocketManager.sendMessage(json)
	}

	fun disconnect() {
		webSocketManager.close()
		coroutineScope.cancel()
	}

	suspend fun loadStep(step: String) {
			database.stepDao().getStep(step)?.let { stepData ->
				sendMessage(gson.toJson(stepData))
			}
	}

	private fun processIncomingMessage(text: String): Step {
		return try {
			stepMapper.mapJsonToStep(text)
		} catch (e: JsonSyntaxException) {
			Step(step = "⚠️ Invalid response format", type = StepType.ERROR)
		}
	}

}
