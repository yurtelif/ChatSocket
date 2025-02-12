package com.yrtelf.chatsocket.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yrtelf.chatsocket.R
import com.yrtelf.chatsocket.ui.chat.Content

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

