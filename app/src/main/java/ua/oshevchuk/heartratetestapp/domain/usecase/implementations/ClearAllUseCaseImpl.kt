package ua.oshevchuk.heartratetestapp.domain.usecase.implementations

import ua.oshevchuk.heartratetestapp.domain.repository.HeartRateRepository
import ua.oshevchuk.heartratetestapp.domain.usecase.interfaces.ClearAllUseCase
import javax.inject.Inject

class ClearAllUseCaseImpl @Inject constructor(private val repository: HeartRateRepository) :
    ClearAllUseCase {
    override suspend fun invoke(): Unit = repository.clearAll()
}