package ua.oshevchuk.heartratetestapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.oshevchuk.heartratetestapp.data.repository.HeartRateRepositoryImpl
import ua.oshevchuk.heartratetestapp.domain.repository.HeartRateRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHeartRateRepository(heartRateRepositoryImpl: HeartRateRepositoryImpl) : HeartRateRepository

}