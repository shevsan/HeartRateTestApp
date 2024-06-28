package ua.oshevchuk.heartratetestapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.oshevchuk.heartratetestapp.data.database.dao.HeartRateDao
import ua.oshevchuk.heartratetestapp.data.database.entities.HeartRateResultDO

@Database(entities = [HeartRateResultDO::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun heartRateDao(): HeartRateDao
}