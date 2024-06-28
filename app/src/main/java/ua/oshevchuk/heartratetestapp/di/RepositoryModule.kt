package ua.oshevchuk.heartratetestapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.oshevchuk.heartratetestapp.data.repository.HeartRateRepositoryImpl
import ua.oshevchuk.heartratetestapp.data.repository.LocalUserRepositoryImpl
import ua.oshevchuk.heartratetestapp.domain.repository.HeartRateRepository
import ua.oshevchuk.heartratetestapp.domain.repository.LocalUserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHeartRateRepository(heartRateRepositoryImpl: HeartRateRepositoryImpl) : HeartRateRepository

    @Binds
    abstract fun bindLocalUserRepository(localUserRepositoryImpl: LocalUserRepositoryImpl) : LocalUserRepository

}