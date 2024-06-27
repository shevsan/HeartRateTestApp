@file:OptIn(ExperimentalPermissionsApi::class)

package ua.oshevchuk.heartratetestapp.ui.screens.measurement

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Size
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.Toast
import androidx.camera.core.Camera
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import ua.oshevchuk.heartratetestapp.R
import java.nio.ByteBuffer

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MeasurementScreen(modifier: Modifier = Modifier, onBackClicked: () -> Unit) {
    val heartRateViewModel: HeartRateViewModel = hiltViewModel()
    val heartRate = remember { mutableIntStateOf(0) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val context = LocalContext.current

    // Collecting the state flows
    LaunchedEffect(Unit) {
        heartRateViewModel.shouldProcess.collect {

        }
    }
    LaunchedEffect(Unit) {
        heartRateViewModel.beatsPerMin.collect {
            heartRate.intValue = it
        }
    }
    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    MeasurementScreenContent(
        modifier = modifier,
        onBackClicked = onBackClicked,
        heartRate = heartRate,
        viewModel = heartRateViewModel,
        context = context
    )
}


@Composable
fun MeasurementScreenContent(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    heartRate: MutableState<Int>,
    viewModel: HeartRateViewModel,
    context: Context
) {
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
        }
    }
    cameraController.enableTorch(true)

    cameraController.apply {
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(context), HeartRateAnalyzer(viewModel))
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    Box(modifier = modifier) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ellipse_background),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.White, CircleShape)
        ) {
            AndroidView(factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    previewView.controller = cameraController
                    cameraController.unbind()
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            })
        }

        Text(
            text = "${heartRate.value} BPM",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(
            onClick = onBackClicked,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(31.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_cross), contentDescription = null)
        }
    }
}

class HeartRateAnalyzer(
    private val viewModel: HeartRateViewModel
) : ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        val buffer: ByteBuffer = image.planes[0].buffer
        viewModel.processImageData(image.width, image.height, buffer)
        image.close()
    }
}




