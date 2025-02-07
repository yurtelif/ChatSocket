package com.yrtelf.chatsocket.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yrtelf.chatsocket.data.util.Converters

@Entity(tableName = "steps")
@TypeConverters(Converters::class)
data class StepEntity(
	@PrimaryKey val step: String,
	val type: String,
	val content: String? = null,
	val action: String? = null
)
