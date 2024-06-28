package ua.oshevchuk.heartratetestapp.domain.usecase.implementations

import ua.oshevchuk.heartratetestapp.domain.repository.HeartRateRepository
import ua.oshevchuk.heartratetestapp.domain.usecase.interfaces.GetAllHeartResultsUseCase
import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity
import javax.inject.Inject

class GetAllHeartResultsUseCaseImpl @Inject constructor(private val repository: HeartRateRepository) :
    GetAllHeartResultsUseCase {
    override suspend fun invoke(): List<HeartRateResultEntity> =
        repository.getAllResults()
}