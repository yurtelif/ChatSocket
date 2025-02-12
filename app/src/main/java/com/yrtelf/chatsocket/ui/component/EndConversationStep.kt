package com.yrtelf.chatsocket.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.yrtelf.chatsocket.R
import com.yrtelf.chatsocket.ui.theme.DisableGray


@Composable
fun EndConversationStep() {
	Text(
		text = stringResource(id = R.string.ending_conversation),
		style = MaterialTheme.typography.bodyMedium,
		color = DisableGray,
		textAlign = TextAlign.Center,
		modifier = Modifier.fillMaxWidth()
	)
}
