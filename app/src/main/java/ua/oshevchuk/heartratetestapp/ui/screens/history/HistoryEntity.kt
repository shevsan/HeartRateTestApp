package ua.oshevchuk.heartratetestapp.ui.screens.history

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

data class HistoryEntity(
    val timestamp : Long,
    val heartRate : Int
){
    fun getPrettyHeartRate() = "$heartRate BPM"
    @SuppressLint("SimpleDateFormat")
    fun getDate() : String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(timestamp * 1000)
        return sdf.format(netDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun getTime() : String{
        val sdf = SimpleDateFormat("HH:mm")
        val netDate = Date(timestamp * 1000)
        return sdf.format(netDate)
    }
}