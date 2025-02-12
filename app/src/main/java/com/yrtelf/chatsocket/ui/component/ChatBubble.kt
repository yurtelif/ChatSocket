package com.yrtelf.chatsocket.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yrtelf.chatsocket.ui.theme.ChatReceiverBg
import com.yrtelf.chatsocket.ui.theme.N11Purple

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
