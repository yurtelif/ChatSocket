package com.yrtelf.chatsocket.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.yrtelf.chatsocket.ui.chat.Button
import com.yrtelf.chatsocket.ui.chat.Content

@Composable
fun ButtonStep(content: Content.ButtonContent, onAction: (Button) -> Unit) {
	Column {
		ChatBubble(content.text, isSender = false)
		content.buttons.forEach { button ->
			OptionButton(button) {
				onAction(button)
			}
		}
	}
}