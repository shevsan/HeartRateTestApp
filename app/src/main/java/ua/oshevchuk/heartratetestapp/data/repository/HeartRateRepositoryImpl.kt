package ua.oshevchuk.heartratetestapp.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.oshevchuk.heartratetestapp.data.database.converters.toDO
import ua.oshevchuk.heartratetestapp.data.database.converters.toEntity
import ua.oshevchuk.heartratetestapp.data.database.dao.HeartRateDao
import ua.oshevchuk.heartratetestapp.di.annotations.IoDispatcher
import ua.oshevchuk.heartratetestapp.domain.repository.HeartRateRepository
import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeartRateRepositoryImpl @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val heartRateDao: HeartRateDao
) : HeartRateRepository {
    override suspend fun insert(result: HeartRateResultEntity) =
        withContext(ioDispatcher){
            heartRateDao.insertResult(result.toDO())
        }


    override suspend fun getAllResults(): List<HeartRateResultEntity> =
        withContext(ioDispatcher){
            heartRateDao.getAllMeasuringResults().map { it.toEntity() }
        }


    override suspend fun clearAll() : Unit = withContext(ioDispatcher){
        heartRateDao.clearAll()
    }
}