@file:OptIn(ExperimentalPermissionsApi::class)

package ua.oshevchuk.heartratetestapp.ui.screens.measurement

import android.Manifest
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import ua.oshevchuk.heartratetestapp.R
import ua.oshevchuk.heartratetestapp.ui.theme.Blue4C

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MeasurementScreen(modifier: Modifier = Modifier, onBackClicked: () -> Unit) {
    val heartRateViewModel: HeartRateViewModel = hiltViewModel()
    val heartRate = remember { mutableIntStateOf(0) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val isFingerCorrectlyPlaced = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        heartRateViewModel.isFingerCorrectlyPlacedState.collect {
            isFingerCorrectlyPlaced.value = it
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

    val context = LocalContext.current

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
        }
    }

    cameraController.apply {
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(context),
            HeartRateAnalyzer(heartRateViewModel)
        )
    }


    MeasurementScreenContent(
        modifier = modifier,
        onBackClicked = onBackClicked,
        heartRate = heartRate,
        cameraController = cameraController,
        isFingerCorrectlyPlaced = isFingerCorrectlyPlaced
    )
}


@Composable
fun MeasurementScreenContent(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    heartRate: MutableState<Int>,
    cameraController: LifecycleCameraController,
    isFingerCorrectlyPlaced: MutableState<Boolean>
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    cameraController.enableTorch(true)
    Box(modifier = modifier) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ellipse_background),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 70.dp, horizontal = 31.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(42.dp),
                border = BorderStroke(2.dp, Blue4C)
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
            AnimatedVisibility(!isFingerCorrectlyPlaced.value) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.finger_is_not_detected),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(id = R.string.finger_is_not_detected_desc),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(266.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_measurement_heart),
                    contentDescription = null
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                    Text(
                        text = "${if (heartRate.value == 0) "--" else heartRate.value}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 62.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = stringResource(id = R.string.bpm),
                        color = Color.White,
                        fontSize = 30.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(46.dp))
            AnimatedVisibility(!isFingerCorrectlyPlaced.value) {
                Image(
                    painter = painterResource(id = R.drawable.ic_put_finger),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }
        }


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