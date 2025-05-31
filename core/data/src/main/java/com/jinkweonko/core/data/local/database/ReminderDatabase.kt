package com.jinkweonko.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jinkweonko.core.data.entity.ReminderEntity

@Database(entities = [ReminderEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ReminderDatabase: RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}