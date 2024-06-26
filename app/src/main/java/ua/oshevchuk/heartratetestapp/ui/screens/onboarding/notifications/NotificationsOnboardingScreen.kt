package ua.oshevchuk.heartratetestapp.ui.screens.onboarding.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.oshevchuk.heartratetestapp.R
import ua.oshevchuk.heartratetestapp.ui.theme.RedFF

@Composable
fun NotificationsOnboardingScreen(modifier: Modifier = Modifier,onNextClicked: () -> Unit) {
    NotificationsOnboardingScreenContent(modifier = modifier, onNextClicked = onNextClicked)
}

@Composable
fun NotificationsOnboardingScreenContent(modifier: Modifier = Modifier, onNextClicked: () -> Unit) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ellipse_background),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 31.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_notifications_onboarding),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = stringResource(id = R.string.notifications),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.notifications_desc),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_onboarding_progress_third),
                contentDescription = null
            )
            Button(
                onClick = onNextClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 31.dp, vertical = 23.dp)
                    .height(44.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = RedFF)
            ) {
                Text(
                    text = stringResource(id = R.string.start),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationsOnboardingScreenContentPreview() {
    NotificationsOnboardingScreenContent(modifier = Modifier.fillMaxSize(), onNextClicked = {})
}