package ua.oshevchuk.heartratetestapp.domain.repository

import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity

interface HeartRateRepository {
    suspend fun insert(result : HeartRateResultEntity)
    suspend fun getAllResults() : List<HeartRateResultEntity>
    suspend fun clearAll()
}