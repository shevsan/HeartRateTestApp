package ua.oshevchuk.heartratetestapp.ui.entities

import android.annotation.SuppressLint
import ua.oshevchuk.heartratetestapp.ui.constants.ResultTypes
import java.text.SimpleDateFormat
import java.util.Date
data class HeartRateResultEntity(
    val timestamp: Long,
    val heartRate: Int
) {
    fun getPrettyHeartRate() = "$heartRate BPM"

    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun getTime(): String {
        val sdf = SimpleDateFormat("HH:mm")
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    }

    fun getResultTypeFromHeartRate() =
        if (heartRate < 60) {
            ResultTypes.SLOWED
        } else if (heartRate in 60..100) {
            ResultTypes.NORMAL
        } else {
            ResultTypes.HURRIED
        }

}