package com.yrtelf.chatsocket.domain

import com.yrtelf.chatsocket.data.util.JsonToRoomRepository
import javax.inject.Inject

class LoadJsonDataUseCase @Inject constructor(
	private val jsonToRoomRepository: JsonToRoomRepository,
) {

	suspend operator fun invoke() {
		jsonToRoomRepository.loadJsonIntoDatabase()
	}
}