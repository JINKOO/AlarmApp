package com.jinkweonko.core.domain.di

import com.jinkweonko.core.domain.usecase.InsertReminderUseCase
import com.jinkweonko.core.domain.usecase.InsertReminderUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class BindUseCase {
    @Binds
    abstract fun bindInsertReminderUseCase(
        insertReminderUseCaseImpl: InsertReminderUseCaseImpl
    ): InsertReminderUseCase

}