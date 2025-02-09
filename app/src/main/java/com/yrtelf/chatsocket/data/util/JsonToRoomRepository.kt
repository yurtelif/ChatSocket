package com.yrtelf.chatsocket.data.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yrtelf.chatsocket.data.entity.StepEntity
import com.yrtelf.chatsocket.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JsonToRoomRepository @Inject constructor(
	private val database: AppDatabase
) {
	suspend fun loadJsonIntoDatabase(context: Context) {
		val jsonString = JsonManager.loadJsonFromAssets(context, "live_support_flow.json") ?: return
		val jsonArray = Gson().fromJson<List<Map<String, Any>>>(jsonString, object : TypeToken<List<Map<String, Any>>>() {}.type)

		withContext(Dispatchers.IO) {
			jsonArray.forEach { json ->
				val step = json["step"] as String
				val type = json["type"] as String
				val action = json["action"] as String
				val content = json["content"] // ✅ Keep content as-is

				val stepEntity = StepEntity(
					step = step,
					type = type,
					action = action,
					content = Gson().toJson(content) // ✅ Store as JSON string
				)
				database.stepDao().insertStep(stepEntity)
			}
		}
	}

}
