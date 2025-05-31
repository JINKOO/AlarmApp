package com.jinkweonko.core.data.di

import com.jinkweonko.core.data.local.source.ReminderLocalDataSource
import com.jinkweonko.core.data.local.source.ReminderLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalSourceModule {
    @Binds
    abstract fun bindReminderLocalDataSource(
        reminderLocalDataSourceImpl: ReminderLocalDataSourceImpl
    ): ReminderLocalDataSource
}