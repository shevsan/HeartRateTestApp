package ua.oshevchuk.heartratetestapp.ui.screens.measurement

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

class HeartRateAnalyzer(
    private val viewModel: HeartRateViewModel
) : ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        val buffer: ByteBuffer = image.planes[0].buffer
        viewModel.processImageData(image.width, image.height, buffer)
        image.close()
    }
}