package ua.oshevchuk.heartratetestapp.domain.usecase.implementations

import ua.oshevchuk.heartratetestapp.domain.repository.HeartRateRepository
import ua.oshevchuk.heartratetestapp.domain.usecase.interfaces.AddHeartRateResultUseCase
import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity
import javax.inject.Inject

class AddHeartRateResultUseCaseImpl @Inject constructor(private val repository: HeartRateRepository) :
    AddHeartRateResultUseCase {
    override suspend fun invoke(result: HeartRateResultEntity) {
        repository.insert(result)
    }
}