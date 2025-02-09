package com.yrtelf.chatsocket.data

import okhttp3.*
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketManager @Inject constructor() {

	private val client = OkHttpClient()
	private var webSocket: WebSocket? = null
	private val request = Request.Builder().url("wss://echo.websocket.org").build()

	fun connect(listener: WebSocketListener) {
		webSocket = client.newWebSocket(request, listener)
	}

	fun sendMessage(step: String) {
		webSocket?.send(step)
	}

	fun close() {
		webSocket?.close(1000, "Connection closed by user")
	}
}
