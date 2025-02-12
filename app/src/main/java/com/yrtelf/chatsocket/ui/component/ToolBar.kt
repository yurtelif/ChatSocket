package com.yrtelf.chatsocket.ui.component

import com.yrtelf.chatsocket.ui.theme.N11Purple
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
	title: String,
) {
	TopAppBar(
		colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = N11Purple),
		title = {
			Box(
				modifier = Modifier.fillMaxWidth(),
				contentAlignment = Alignment.Center
			) {
				Text(
					text = title,
					color = Color.White,
					fontSize = 20.sp,
					textAlign = TextAlign.Center,
					modifier = Modifier.fillMaxWidth()
				)
			}
		}
	)
}

@Preview
@Composable
fun ToolbarPreview() {
	Toolbar(title = "Chat Support")
}
