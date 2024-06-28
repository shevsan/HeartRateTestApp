package ua.oshevchuk.heartratetestapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ua.oshevchuk.heartratetestapp.data.database.entities.HeartRateResultDO

@Dao
interface HeartRateDao {
    @Upsert
    suspend fun insertResult(heartRateResultDO: HeartRateResultDO)

    @Query("DELETE FROM heart_results")
    suspend fun clearAll()

    @Query("SELECT * FROM heart_results ORDER BY timestamp")
    suspend fun getAllMeasuringResults() : List<HeartRateResultDO>
}