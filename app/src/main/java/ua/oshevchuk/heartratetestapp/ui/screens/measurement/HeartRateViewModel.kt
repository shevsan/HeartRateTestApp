package ua.oshevchuk.heartratetestapp.ui.screens.measurement

import ImageProcessing
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity
import java.nio.ByteBuffer
import javax.inject.Inject

@HiltViewModel
class HeartRateViewModel @Inject constructor() : ViewModel() {
    private val imageProcessing = ImageProcessing()
    private var beatsIndex = 0
    private val beatsArraySize = 9
    private val beatsArray = IntArray(beatsArraySize)
    private var averageIndex = 0
    private val averageArraySize = 10
    private val averageArray = IntArray(averageArraySize)
    private var currentType = Type.GREEN
    private var beats = 0.0
    private var startTime: Long = 0

    private val _isFingerCorrectlyPlacedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFingerCorrectlyPlacedState = _isFingerCorrectlyPlacedState.asStateFlow()

    private val _beatsPerMin: MutableStateFlow<Int> = MutableStateFlow(0)
    val beatsPerMin = _beatsPerMin.asStateFlow()

    private val _progressState: MutableStateFlow<Float> = MutableStateFlow(0f)
    val progressState = _progressState.asStateFlow()

    private val _resultRateState: MutableStateFlow<HeartRateResultEntity?> = MutableStateFlow(null)
    val resultRateState = _resultRateState.asStateFlow()

    fun processImageData(width: Int, height: Int, byteBuffer: ByteBuffer?) {
        val imgAvg = imageProcessing.decodeYUV420SPtoRedAvg(width, height, byteBuffer)
        if (imgAvg > 80) {
            _isFingerCorrectlyPlacedState.value = false
            _beatsPerMin.value = 0
            _progressState.value = 0F
            return
        } else {
            _isFingerCorrectlyPlacedState.value = true
        }

        var averageArrayAvg = 0
        var averageArrayCnt = 0
        for (i in averageArray.indices) {
            if (averageArray[i] > 0) {
                averageArrayAvg += averageArray[i]
                averageArrayCnt++
            }
        }

        val rollingAverage = if (averageArrayCnt > 0) averageArrayAvg / averageArrayCnt else 0
        var newType: Type = currentType
        if (imgAvg < rollingAverage) {
            newType = Type.RED
            if (newType != currentType) {
                beats++
            }
        } else if (imgAvg > rollingAverage) {
            newType = Type.GREEN
        }


        if (averageIndex == averageArraySize) averageIndex = 0
        averageArray[averageIndex] = imgAvg
        averageIndex++

        if (newType != currentType) {
            currentType = newType
        }

        val endTime = System.currentTimeMillis()
        val totalTimeInSecs: Double =
            (endTime - startTime) / 1000.0

        val progress = (totalTimeInSecs / 10).toFloat()
        _progressState.value = progress.coerceIn(0f, 1f)

        if (totalTimeInSecs >= 10) {
            val bps: Double = beats / totalTimeInSecs
            val dpm = (bps * 60.0).toInt()
            if (dpm < 30 || dpm > 180) {
                startTime = System.currentTimeMillis()
                beats = 0.0
                _progressState.value = 0F
                _isFingerCorrectlyPlacedState.value = false
                return
            }

            if (beatsIndex == beatsArraySize) beatsIndex = 0
            beatsArray[beatsIndex] = dpm
            beatsIndex++
            var beatsArrayAvg = 0
            var beatsArrayCnt = 0
            for (i in beatsArray.indices) {
                if (beatsArray[i] > 0) {
                    beatsArrayAvg += beatsArray[i]
                    beatsArrayCnt++
                }
            }
            val beatsAvg = beatsArrayAvg / beatsArrayCnt
            _beatsPerMin.value = beatsAvg
            startTime = System.currentTimeMillis()
            _resultRateState.value = HeartRateResultEntity(timestamp = startTime, beatsAvg)
            beats = 0.0
        }
    }
}