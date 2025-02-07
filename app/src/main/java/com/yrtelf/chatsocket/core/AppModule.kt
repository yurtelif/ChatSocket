package com.yrtelf.chatsocket.core

import android.content.Context
import com.yrtelf.chatsocket.data.WebSocketManager
import com.yrtelf.chatsocket.data.dao.StepDao
import com.yrtelf.chatsocket.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun provideWebSocketManager(): WebSocketManager {
		return WebSocketManager()
	}

	@Provides
	@Singleton
	fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
		return AppDatabase.getInstance(context)
	}

	@Provides
	fun provideStepDao(database: AppDatabase): StepDao {
		return database.stepDao()
	}

	@Provides
	@Singleton
	fun provideApplicationContext(@ApplicationContext context: Context): Context {
		return context
	}
}
