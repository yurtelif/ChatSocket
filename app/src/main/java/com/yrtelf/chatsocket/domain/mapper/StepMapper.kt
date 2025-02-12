package com.yrtelf.chatsocket.domain.mapper

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.yrtelf.chatsocket.ui.chat.Button
import com.yrtelf.chatsocket.ui.chat.Content
import com.yrtelf.chatsocket.ui.chat.Step
import com.yrtelf.chatsocket.ui.chat.StepType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepMapper @Inject constructor(private val gson: Gson) {

	fun mapJsonToStep(jsonMessage: String): Step? {
		return try {
			val jsonObject = gson.fromJson(jsonMessage, JsonObject::class.java)
			val step = jsonObject["step"].asString
			val type = StepType.fromString(jsonObject["type"].asString)
			val action = jsonObject["action"].asString

			val contentElement = jsonObject["content"]

			val content = when {
				type == StepType.TEXT && contentElement.isJsonPrimitive ->
					Content.TextContent(contentElement.asString)

				type == StepType.BUTTON && contentElement.isJsonPrimitive -> {
					val contentObj = gson.fromJson(contentElement.asString, JsonObject::class.java)
					val text = contentObj["text"]?.asString ?: ""
					val buttons: List<Button> = if (contentObj.has("buttons") && contentObj["buttons"].isJsonArray) {
						gson.fromJson(contentObj["buttons"].asJsonArray, object : TypeToken<List<Button>>() {}.type)
					} else {
						emptyList()
					}
					Content.ButtonContent(text, buttons)
				}

				type == StepType.IMAGE && contentElement.isJsonPrimitive ->
					Content.ImageContent(contentElement.asString)

				else -> throw IllegalArgumentException("Invalid content type for step: $step")
			}

			Step(step, type, content, action)
		} catch (ex: JsonSyntaxException) {
			Log.e("WebSocketParse", "processIncomingMessage: invalid response format", )
			null
		} catch (ex: Exception) {
			Log.e("WebSocketParse", "processIncomingMessage: error parsing step", )
			null
		}
	}
}
