package com.yrtelf.chatsocket.domain

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.yrtelf.chatsocket.ui.chat.Step
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.google.gson.reflect.TypeToken
import com.yrtelf.chatsocket.ui.chat.Button
import com.yrtelf.chatsocket.ui.chat.Content
import com.yrtelf.chatsocket.ui.chat.StepType
import kotlinx.coroutines.flow.flowOf

class ProcessWebSocketMessageUseCase @Inject constructor(
	private val gson: Gson
) {
	fun processMessage(messageFlow: String): Flow<Step> {
		return flowOf( parseStep(messageFlow))

	}

	private fun parseStep(jsonMessage: String): Step {
		try {
			val jsonObject = gson.fromJson(jsonMessage, JsonObject::class.java)
			val step = jsonObject["step"].asString
			val type = StepType.fromString(jsonObject["type"].asString)
			val action = jsonObject["action"].asString

			val contentElement = jsonObject["content"]

			val content = when {
				type == StepType.TEXT && contentElement.isJsonPrimitive ->
					Content.TextContent(contentElement.asString)

				type == StepType.BUTTON && contentElement.isJsonPrimitive -> {
					// ✅ Parse JSON string into an actual object
					val contentObj = gson.fromJson(contentElement.asString, JsonObject::class.java)
					val text = contentObj["text"]?.asString ?: ""
					val buttons: List<Button> = if (contentObj.has("buttons") && contentObj["buttons"].isJsonArray) {
						gson.fromJson(contentObj["buttons"].asJsonArray, object : TypeToken<List<Button>>() {}.type)
					} else {
						emptyList() // Default to empty list if "buttons" is missing or not an array
					}
					Content.ButtonContent(text, buttons)
				}

				type == StepType.IMAGE && contentElement.isJsonPrimitive ->
					Content.ImageContent(contentElement.asString)

				else -> throw IllegalArgumentException("Invalid content type for step: $step")
			}


			return Step(step, type, content, action)
		} catch (ex: Exception){
			return Step(step = "")
		}

	}
}
