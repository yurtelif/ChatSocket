package com.yrtelf.chatsocket.domain

import android.content.Context
import com.yrtelf.chatsocket.data.util.JsonToRoomRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoadJsonDataUseCase @Inject constructor(
	private val jsonToRoomRepository: JsonToRoomRepository,
	@ApplicationContext private val context: Context
) {

	suspend operator fun invoke() {
		jsonToRoomRepository.loadJsonIntoDatabase(context)
	}
}