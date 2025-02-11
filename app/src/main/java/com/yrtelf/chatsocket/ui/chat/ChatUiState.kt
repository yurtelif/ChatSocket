package com.yrtelf.chatsocket.ui.chat

enum class StepType {
	BUTTON,
	TEXT,
	IMAGE,
	ANSWER,
	ERROR;

	companion object {
		fun fromString(type: String): StepType {
			return when (type.lowercase()) {
				"button" -> BUTTON
				"text" -> TEXT
				"image" -> IMAGE
				else -> TEXT
			}
		}
	}
}

data class Step(
	val step: String?,
	val type: StepType? = null,
	val content: Content? = null,
	val action: String? = null,
	val isSender: Boolean = false
)

sealed class Content {
	data class TextContent(val text: String) : Content()
	data class ButtonContent(val text: String, val buttons: List<Button>) : Content()
	data class ImageContent(val imageUrl: String) : Content()
}

data class Button(
	val label: String,
	val action: String
)