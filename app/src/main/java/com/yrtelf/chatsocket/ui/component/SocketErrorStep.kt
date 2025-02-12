package com.yrtelf.chatsocket.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.yrtelf.chatsocket.R
import com.yrtelf.chatsocket.ui.chat.Step
import com.yrtelf.chatsocket.ui.theme.SocketErrorRed

@Composable
fun SocketErrorStep(step: Step) {
	Text(
		text = step.step?: stringResource(R.string.socket_error_default),
		style = MaterialTheme.typography.bodyMedium,
		color = SocketErrorRed,
		textAlign = TextAlign.Center,
		modifier = Modifier.fillMaxWidth()
	)
}