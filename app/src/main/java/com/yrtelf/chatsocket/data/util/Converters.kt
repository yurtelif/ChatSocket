package com.yrtelf.chatsocket.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class Converters {
	private val gson = Gson()

	@TypeConverter
	fun fromContent(content: Any?): String {
		return gson.toJson(content)
	}

	@TypeConverter
	fun toContent(contentString: String?): Any? {
		if (contentString.isNullOrEmpty()) return null

		return try {
			val mapType = object : TypeToken<Map<String, Any>>() {}.type
			gson.fromJson<Map<String, Any>>(contentString, mapType)
		} catch (e: JsonSyntaxException) {
			contentString // If it fails, return as String
		}
	}
}



