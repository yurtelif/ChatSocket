package com.yrtelf.chatsocket.data

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketManager @Inject constructor() {
	private val client = OkHttpClient()
	private var webSocket: WebSocket? = null

	private val _incomingMessages = MutableSharedFlow<String>(extraBufferCapacity = 1)
	val incomingMessages = _incomingMessages.asSharedFlow()

	private val _connectionState = MutableStateFlow(WebSocketState.DISCONNECTED)
	val connectionState: StateFlow<WebSocketState> = _connectionState.asStateFlow()


	fun connect() {
		if (webSocket != null) return

		val request = Request.Builder().url(WEBSOCKET_URL).build()
		webSocket = client.newWebSocket(request, object : WebSocketListener() {
			override fun onOpen(webSocket: WebSocket, response: Response) {
				_connectionState.value = WebSocketState.CONNECTED
			}

			override fun onMessage(webSocket: WebSocket, text: String) {
				_incomingMessages.tryEmit(text)
			}

			override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
				_connectionState.value = WebSocketState.DISCONNECTED
				Log.e("WebSocket", "Connection failed: ${t.message}")
			}

			override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
				_connectionState.value = WebSocketState.DISCONNECTED
			}
		})
	}

	fun sendMessage(message: String) {
		webSocket?.send(message)
	}

	fun close() {
		webSocket?.close(1000, "Closing connection")
		webSocket = null
		_connectionState.value = WebSocketState.DISCONNECTED
	}

	companion object SocketConstants {
		const val WEBSOCKET_URL = "wss://echo.websocket.org"
	}

	enum class WebSocketState {
		CONNECTED, DISCONNECTED
	}

}
