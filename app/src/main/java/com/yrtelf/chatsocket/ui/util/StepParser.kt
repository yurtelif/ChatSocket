package com.yrtelf.chatsocket.ui.util

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.yrtelf.chatsocket.ui.chat.Button
import com.yrtelf.chatsocket.ui.chat.Content
import com.yrtelf.chatsocket.ui.chat.Step
import com.yrtelf.chatsocket.ui.chat.StepType
import java.lang.reflect.Type

class StepParser : JsonDeserializer<Step> {
	override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Step {
		val jsonObject = json.asJsonObject
		val step = jsonObject.get("step").asString
		val type = StepType.fromString(jsonObject.get("type").asString)
		val action = jsonObject.get("action").asString

		val content = when (type) {
			StepType.TEXT -> Content.TextContent(jsonObject.get("content").asString)
			StepType.BUTTON -> {
				val contentObj = jsonObject.getAsJsonObject("content")
				val text = contentObj.get("text").asString
				val buttons = context.deserialize<List<Button>>(contentObj.get("buttons"), object : TypeToken<List<Button>>() {}.type)
				Content.ButtonContent(text, buttons)
			}
			StepType.IMAGE -> Content.ImageContent(jsonObject.get("content").asString)
			StepType.ANSWER -> {
				Content.TextContent(step)
			}
		}

		return Step(step, type, content, action)
	}
}
