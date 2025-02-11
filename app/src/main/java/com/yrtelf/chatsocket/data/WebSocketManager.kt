package com.yrtelf.chatsocket.data

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketManager @Inject constructor() {
	private val client = OkHttpClient()
	private var webSocket: WebSocket? = null
	private val _incomingMessages = MutableSharedFlow<String>(extraBufferCapacity = 1)
	val incomingMessages = _incomingMessages.asSharedFlow()

	fun connect() {
		val request = Request.Builder().url(WEBSOCKET_URL).build()
		webSocket = client.newWebSocket(request, object : WebSocketListener() {
			override fun onMessage(webSocket: WebSocket, text: String) {
				_incomingMessages.tryEmit(text)
			}

			override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
				Log.e("WebSocket", "Connection failed: ${t.message}")
			}
		})
	}

	fun sendMessage(message: String) {
		webSocket?.send(message)
	}

	fun close() {
		webSocket?.close(1000, "Closing connection")
	}

	companion object SocketConstants {
		const val WEBSOCKET_URL = "wss://echo.websocket.org"
	}

}
