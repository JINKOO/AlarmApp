package com.jinkweonko.core.data.local.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideReminderDatabase(
        @ApplicationContext context: Context
    ): ReminderDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = ReminderDatabase::class.java,
            name = "reminder_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideReminderDao(
        database: ReminderDatabase
    ): ReminderDao = database.reminderDao()
}