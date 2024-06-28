package ua.oshevchuk.heartratetestapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import ua.oshevchuk.heartratetestapp.R
import ua.oshevchuk.heartratetestapp.ui.navigation.Navigation
import ua.oshevchuk.heartratetestapp.ui.theme.HeartRateTestAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val cameraPermissionRequestLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(
                    this,
                    getString(R.string.camera_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun handleCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED -> {
                cameraPermissionRequestLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HeartRateTestAppTheme {
                Navigation()
            }
        }
        handleCameraPermission()

    }
}
