package ua.oshevchuk.heartratetestapp.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import ua.oshevchuk.heartratetestapp.R

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onLoaded: () -> Unit) {
    SplashScreenContent(
        modifier = modifier,
        onLoaded = onLoaded
    )
}


@Composable
fun SplashScreenContent(modifier: Modifier = Modifier, onLoaded: () -> Unit) {
    Scaffold {
        Box(modifier = modifier.padding(it)) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ellipse_background),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.Black,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            AnimatedPreloader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 31.dp, vertical = 10.dp)
                    .height(15.dp)
                    .align(Alignment.BottomCenter)
            )


            LaunchedEffect(Unit) {
                //Temp delay for demonstrating loader
                delay(800L)
                onLoaded()
            }
        }
    }
}

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.loading_animation
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = 1,
        isPlaying = true
    )


    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashScreenContentPreview() {
    SplashScreenContent(modifier = Modifier
        .fillMaxSize(),
        onLoaded = {})
}