package ua.oshevchuk.heartratetestapp.data.database.converters

import ua.oshevchuk.heartratetestapp.data.database.entities.HeartRateResultDO
import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity

fun HeartRateResultEntity.toDO() = HeartRateResultDO(
    timestamp = this.timestamp,
    heartRate = this.heartRate
)

fun HeartRateResultDO.toEntity() = HeartRateResultEntity(
    timestamp = this.timestamp ?: 0,
    heartRate = this.heartRate ?: 0
)