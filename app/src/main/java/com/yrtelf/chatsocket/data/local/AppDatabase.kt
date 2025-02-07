package com.yrtelf.chatsocket.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yrtelf.chatsocket.data.dao.StepDao
import com.yrtelf.chatsocket.data.entity.StepEntity
import com.yrtelf.chatsocket.data.util.Converters

@Database(entities = [StepEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

	abstract fun stepDao(): StepDao

	companion object {
		@Volatile private var instance: AppDatabase? = null

		fun getInstance(context: Context): AppDatabase {
			return instance ?: synchronized(this) {
				instance ?: Room.databaseBuilder(
					context.applicationContext,
					AppDatabase::class.java,
					"chat_database"
				).build().also {
					instance = it
				}
			}
		}
	}
}
