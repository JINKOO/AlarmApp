package com.jinkweonko.core.domain.di

import com.jinkweonko.core.domain.usecase.GetAllReminderUseCase
import com.jinkweonko.core.domain.usecase.GetAllReminderUseCaseImpl
import com.jinkweonko.core.domain.usecase.GetReminderUseCase
import com.jinkweonko.core.domain.usecase.GetReminderUseCaseImpl
import com.jinkweonko.core.domain.usecase.InsertReminderUseCase
import com.jinkweonko.core.domain.usecase.InsertReminderUseCaseImpl
import com.jinkweonko.core.domain.usecase.UpdateReminderUseCase
import com.jinkweonko.core.domain.usecase.UpdateReminderUseCaseImpl
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

    @Binds
    abstract fun bindGetAllReminderUseCase(
        getAllReminderUseCaseImpl: GetAllReminderUseCaseImpl
    ): GetAllReminderUseCase

    @Binds
    abstract fun bindUpdateReminderUseCase(
        updateReminderUseCaseImpl: UpdateReminderUseCaseImpl
    ): UpdateReminderUseCase

    @Binds
    abstract fun bindGetReminderUseCase(
        useCase: GetReminderUseCaseImpl
    ): GetReminderUseCase
}