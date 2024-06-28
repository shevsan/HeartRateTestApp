package ua.oshevchuk.heartratetestapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "heart_results")
data class HeartRateResultDO(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val timestamp : Long? = 0,
    val heartRate : Int? = 0
)