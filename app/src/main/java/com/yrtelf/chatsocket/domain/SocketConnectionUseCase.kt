package com.yrtelf.chatsocket.domain

import com.yrtelf.chatsocket.data.WebSocketManager
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SocketConnectionUseCase @Inject constructor(
	private val webSocketManager: WebSocketManager
) {

	enum class ConnectionAction { CONNECT, DISCONNECT }

	operator fun invoke(action: ConnectionAction) {
		when (action) {
			ConnectionAction.CONNECT -> webSocketManager.connect()
			ConnectionAction.DISCONNECT -> webSocketManager.close()
		}
	}

	fun observeConnectionState(): StateFlow<WebSocketManager.WebSocketState> = webSocketManager.connectionState

}
