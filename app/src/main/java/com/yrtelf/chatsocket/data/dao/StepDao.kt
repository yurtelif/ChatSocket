package com.yrtelf.chatsocket.data.dao

import androidx.room.*
import com.yrtelf.chatsocket.data.entity.StepEntity

@Dao
interface StepDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertStep(step: StepEntity)

	@Query("SELECT * FROM steps WHERE step = :step LIMIT 1")
	suspend fun getStep(step: String): StepEntity?
}
