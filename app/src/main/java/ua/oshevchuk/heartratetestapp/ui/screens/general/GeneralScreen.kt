package ua.oshevchuk.heartratetestapp.ui.screens.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
fun GeneralScreen(
    modifier: Modifier = Modifier,
    onHistoryClicked: () -> Unit,
    onStartMeasure: () -> Unit
) {
    Scaffold {
        GeneralScreenContent(
            modifier = modifier.padding(it),
            onHistoryClicked = onHistoryClicked,
            onStartMeasure = onStartMeasure
        )
    }

}

@Composable
fun GeneralScreenContent(
    modifier: Modifier = Modifier,
    onHistoryClicked: () -> Unit,
    onStartMeasure: () -> Unit
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ellipse_background),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(RedFF), contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable(onClick = onHistoryClicked)
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.history),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_history),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(45.dp)
                            .padding(8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(45.dp))
            Text(
                text = stringResource(id = R.string.first_test),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 31.dp),
                textAlign = TextAlign.Center
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = null,
            modifier = Modifier
                .width(284.dp)
                .align(Alignment.Center)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_start_recording),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
                .clickable {
                    onStartMeasure()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GeneralScreenContentPreview() {
    GeneralScreenContent(
        modifier = Modifier.fillMaxSize(),
        onHistoryClicked = {},
        onStartMeasure = {})
}