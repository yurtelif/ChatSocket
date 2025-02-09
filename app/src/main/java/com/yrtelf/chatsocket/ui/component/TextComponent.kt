package com.yrtelf.chatsocket.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
			null -> {}
		}
	}
}


@Composable
fun ButtonStep(content: Content.ButtonContent, onAction: (Button) -> Unit) {
	Column {
		ChatBubble(content.text, isSender = false)
		content.buttons.forEach { button ->
			Button(onClick = { onAction(button) }, modifier = Modifier.padding(top = 8.dp)) {
				Text(button.label)
			}
		}
	}
}

@Composable
fun ChatBubble(
	message: String,
	isSender: Boolean
) {
	val bubbleColor = if (isSender) Color(0xFFDCF8C6) else Color.LightGray // WhatsApp green for sender
	val alignment = if (isSender) Alignment.End else Alignment.Start
	val shape = if (isSender) RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp)
	else RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)

	Box(
		modifier = Modifier
			.fillMaxWidth()
			.padding(4.dp)
			.wrapContentWidth(alignment)
			.background(bubbleColor, shape)
			.padding(12.dp)
	) {
		Text(
			text = message,
			style = MaterialTheme.typography.bodyMedium,
			color = Color.Black,
			modifier = Modifier.align(Alignment.CenterStart)
		)
	}
}


@Composable
fun ImageStep(content: Content.ImageContent) {
	/**
	AsyncImage(
		model = content.imageUrl,
		contentDescription = "Step Image",
		modifier = Modifier.fillMaxWidth()
	)**/
}

