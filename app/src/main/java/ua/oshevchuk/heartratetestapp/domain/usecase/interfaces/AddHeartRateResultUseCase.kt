package ua.oshevchuk.heartratetestapp.domain.usecase.interfaces

import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity

interface AddHeartRateResultUseCase {
    suspend operator fun invoke(result : HeartRateResultEntity)
}