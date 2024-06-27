package ua.oshevchuk.heartratetestapp.ui.screens.history

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
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FloatingActionButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.oshevchuk.heartratetestapp.R
import ua.oshevchuk.heartratetestapp.ui.theme.RedFF

@Composable
fun HistoryScreen(modifier: Modifier = Modifier, onBackClicked: () -> Unit) {
    Scaffold {
        HistoryScreenContent(
            modifier = modifier.padding(it),
            onBackClicked = onBackClicked,
            onClearHistoryClicked = {}
        )
    }
}

@Composable
fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onClearHistoryClicked: () -> Unit
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ellipse_background),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(RedFF), contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable(onClick = onBackClicked)
                        .padding(vertical = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(45.dp)
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.width(26.dp))
                    Text(
                        text = stringResource(id = R.string.history),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {

            }
        }
        FloatingActionButton(
            onClick = onClearHistoryClicked,
            shape = RoundedCornerShape(25.dp),
            backgroundColor = RedFF,
            modifier = Modifier
                .fillMaxWidth()
                .height(59.dp)
                .padding(horizontal = 31.dp, vertical = 15.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(id = R.string.clear_history),
                color = Color.White,
                fontSize = 19.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenContentPreview() {
    HistoryScreenContent(
        modifier = Modifier
            .fillMaxSize(),
        onBackClicked = {},
        onClearHistoryClicked = {})
}