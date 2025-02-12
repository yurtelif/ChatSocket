package com.yrtelf.chatsocket.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yrtelf.chatsocket.ui.chat.Button
import com.yrtelf.chatsocket.ui.theme.PurpleBtnInside
import com.yrtelf.chatsocket.ui.theme.PurpleBtnOutline
import com.yrtelf.chatsocket.ui.theme.PurpleTextColor

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