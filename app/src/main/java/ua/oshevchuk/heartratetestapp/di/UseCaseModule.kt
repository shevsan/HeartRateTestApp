package ua.oshevchuk.heartratetestapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.oshevchuk.heartratetestapp.domain.usecase.implementations.AddHeartRateResultUseCaseImpl
import ua.oshevchuk.heartratetestapp.domain.usecase.implementations.ClearAllUseCaseImpl
import ua.oshevchuk.heartratetestapp.domain.usecase.implementations.GetAllHeartResultsUseCaseImpl
import ua.oshevchuk.heartratetestapp.domain.usecase.interfaces.AddHeartRateResultUseCase
import ua.oshevchuk.heartratetestapp.domain.usecase.interfaces.ClearAllUseCase
import ua.oshevchuk.heartratetestapp.domain.usecase.interfaces.GetAllHeartResultsUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindAddHeartRateResultUseCase(addHeartRateResultUseCaseImpl: AddHeartRateResultUseCaseImpl): AddHeartRateResultUseCase

    @Binds
    abstract fun bindClearAllUseCase(clearAllUseCaseImpl: ClearAllUseCaseImpl): ClearAllUseCase

    @Binds
    abstract fun bindGetAllHeartResultsUseCase(getAllHeartResultsUseCaseImpl: GetAllHeartResultsUseCaseImpl): GetAllHeartResultsUseCase
}