package com.yrtelf.chatsocket.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yrtelf.chatsocket.ui.chat.Button
import com.yrtelf.chatsocket.ui.chat.Content
import com.yrtelf.chatsocket.ui.chat.Step
import com.yrtelf.chatsocket.ui.chat.StepType

@Composable
fun StepScreen(step: Step, onAction: (Button) -> Unit) {
	Column(modifier = Modifier
		.fillMaxSize()
		.padding(8.dp)) {
		when (step.type) {
			StepType.TEXT -> {
				val content = step.content as Content.TextContent
				ChatBubble(content.text, isSender = false)
			}

			StepType.BUTTON -> ButtonStep(step.content as Content.ButtonContent, onAction)
			StepType.IMAGE -> ImageStep(step.content as Content.ImageContent)
			StepType.ANSWER -> ChatBubble(step.step ?: "", isSender =  true  )
			StepType.END_CONVERSATION -> EndConversationStep()
			StepType.ERROR -> SocketErrorStep(step)
			else -> {}
		}
	}
}
