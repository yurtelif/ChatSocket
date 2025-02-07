package com.yrtelf.chatsocket.data.util

import android.content.Context
import java.io.IOException

object JsonManager {

	fun loadJsonFromAssets(context: Context, fileName: String): String? {
		return try {
			context.assets.open(fileName).bufferedReader().use { it.readText() }
		} catch (e: IOException) {
			e.printStackTrace()
			null
		}
	}
}
