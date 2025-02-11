package com.yrtelf.chatsocket.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yrtelf.chatsocket.R
import com.yrtelf.chatsocket.ui.chat.Button
import com.yrtelf.chatsocket.ui.chat.Content
import com.yrtelf.chatsocket.ui.chat.Step
import com.yrtelf.chatsocket.ui.chat.StepType
import com.yrtelf.chatsocket.ui.theme.ChatReceiverBg
import com.yrtelf.chatsocket.ui.theme.N11Purple
import com.yrtelf.chatsocket.ui.theme.PurpleBtnInside
import com.yrtelf.chatsocket.ui.theme.PurpleBtnOutline
import com.yrtelf.chatsocket.ui.theme.PurpleTextColor


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
			else -> {}
		}
	}
}


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

@Composable
fun ChatBubble(
	message: String,
	isSender: Boolean
) {
	val bubbleColor = if (isSender) N11Purple else ChatReceiverBg
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
			color =  if (isSender) Color.White else Color.Black,
			modifier = Modifier.align(Alignment.CenterStart)
		)
	}
}

@Composable
fun OptionButton(button: Button, onAction: (Button) -> Unit) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier
			.width(256.dp)
			.padding(8.dp)
			.clip(RoundedCornerShape(8.dp))
			.border(2.dp, PurpleBtnOutline, RoundedCornerShape(8.dp))
			.background(PurpleBtnInside)
			.clickable { onAction(button) }
			.padding(horizontal = 16.dp, vertical = 12.dp)
	) {
		Text(
			text = button.label,
			color = PurpleTextColor,
			style = MaterialTheme.typography.labelLarge,
			textAlign = TextAlign.Center)
	}
}

@Composable
fun ImageStep(content: Content.ImageContent) {
	AsyncImage(
		model = ImageRequest.Builder(LocalContext.current)
			.data(content.imageUrl)
			.placeholder(R.drawable.placeholder)
			.error(R.drawable.placeholder)
			.build(),
		contentDescription = "Network Image"
	)
}

