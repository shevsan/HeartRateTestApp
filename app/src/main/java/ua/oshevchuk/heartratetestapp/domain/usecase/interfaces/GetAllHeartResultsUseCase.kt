package ua.oshevchuk.heartratetestapp.domain.usecase.interfaces

import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity

interface GetAllHeartResultsUseCase {
    suspend operator fun invoke() : List<HeartRateResultEntity>
}