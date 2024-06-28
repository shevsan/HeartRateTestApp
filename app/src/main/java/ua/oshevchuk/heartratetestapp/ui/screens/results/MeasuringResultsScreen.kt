package ua.oshevchuk.heartratetestapp.ui.screens.results

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import ua.oshevchuk.heartratetestapp.R
import ua.oshevchuk.heartratetestapp.common.Response
import ua.oshevchuk.heartratetestapp.common.noRippleClickable
import ua.oshevchuk.heartratetestapp.ui.constants.ResultTypes
import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity
import ua.oshevchuk.heartratetestapp.ui.theme.Blue21
import ua.oshevchuk.heartratetestapp.ui.theme.BlueB2
import ua.oshevchuk.heartratetestapp.ui.theme.GrayA1
import ua.oshevchuk.heartratetestapp.ui.theme.GrayAE
import ua.oshevchuk.heartratetestapp.ui.theme.GrayEC
import ua.oshevchuk.heartratetestapp.ui.theme.Green1C
import ua.oshevchuk.heartratetestapp.ui.theme.Red4C
import ua.oshevchuk.heartratetestapp.ui.theme.RedFF

@Composable
fun MeasuringResultScreen(
    modifier: Modifier = Modifier,
    measuringResults: HeartRateResultEntity,
    onDoneClicked: () -> Unit,
    onHistoryClicked: () -> Unit,
    viewModel: MeasuringResultsViewModel = hiltViewModel()
) {
    when (viewModel.insertResultState.collectAsState().value) {
        is Response.Error -> {
            val context = LocalContext.current
            val errorText =  stringResource(id = R.string.smth_went_wrong)
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    errorText,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        is Response.Loading -> {}
        else -> {}
    }
    LaunchedEffect(measuringResults) {
        viewModel.insertResult(measuringResults)
    }
    MeasuringResultScreenContent(
        modifier = modifier,
        measuringResults = measuringResults,
        onDoneClicked = onDoneClicked,
        onHistoryClicked = onHistoryClicked
    )
}

@Composable
fun MeasuringResultScreenContent(
    modifier: Modifier = Modifier,
    onDoneClicked: () -> Unit,
    measuringResults: HeartRateResultEntity,
    onHistoryClicked: () -> Unit
) {
    Scaffold {
        Box(modifier = modifier.padding(it)) {
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
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.results),
                            color = White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.noRippleClickable(onClick = onHistoryClicked)
                        ) {
                            Text(
                                text = stringResource(id = R.string.history),
                                color = White,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_history),
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }
            val layoutDirection = LocalLayoutDirection.current
            ResultsItem(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 15.dp),
                entity = measuringResults,
                paddingValues = it.calculateRightPadding(layoutDirection) + it.calculateLeftPadding(layoutDirection)
            )
            FloatingActionButton(
                onClick = onDoneClicked,
                shape = RoundedCornerShape(25.dp),
                containerColor = RedFF,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 31.dp, vertical = 15.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = stringResource(id = R.string.done),
                    color = White,
                    fontSize = 19.sp
                )
            }
        }
    }
}

@Composable
fun ResultsItem(modifier: Modifier = Modifier, entity: HeartRateResultEntity, paddingValues: Dp) {
    val resultType = entity.getResultTypeFromHeartRate()
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = GrayEC),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.your_results),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = resultType.titleRes),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = resultType.color
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${entity.getTime()}\n${entity.getDate()}",
                        fontSize = 18.sp,
                        color = Black
                    )
                }
            }
            HeartRateProgressBar(entity.heartRate, paddingValues)
            Spacer(modifier = Modifier.height(30.dp))
            HeartRateSegment(
                modifier = Modifier.fillMaxWidth(),
                isSelected = resultType == ResultTypes.SLOWED,
                resultType = ResultTypes.SLOWED
            )
            Spacer(modifier = Modifier.height(12.dp))
            HeartRateSegment(
                modifier = Modifier.fillMaxWidth(),
                isSelected = resultType == ResultTypes.NORMAL,
                resultType = ResultTypes.NORMAL
            )
            Spacer(modifier = Modifier.height(12.dp))
            HeartRateSegment(
                modifier = Modifier.fillMaxWidth(),
                isSelected = resultType == ResultTypes.HURRIED,
                resultType = ResultTypes.HURRIED
            )
        }
    }
}


@Composable
fun HeartRateSegment(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    resultType: ResultTypes
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .defaultMinSize(minWidth = 120.dp),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(containerColor = BlueB2)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(3.dp)) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier.size(8.dp),
                    colors = CardDefaults.cardColors(containerColor = resultType.color)
                ) {}
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(id = resultType.titleRes),
                    fontSize = 12.sp,
                    color = Black
                )
            }
        }
        Text(
            text = resultType.rangeDesc,
            color = if (isSelected) Black else GrayA1,
            fontSize = 12.sp
        )
    }
}


@Composable
fun HeartRateProgressBar(heartRate: Int, paddingValues: Dp) {
    val slices = listOf(
        Slice(value = 33.3f, color = Blue21),
        Slice(value = 33.3f, color = Green1C),
        Slice(value = 33.3f, color = Red4C)
    )
    var pointerOffset by remember { mutableFloatStateOf(0f) }
    val totalHeartRateRange = 180f
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp - 35.dp - paddingValues
    val animatedOffset = animateFloatAsState(
        targetValue = pointerOffset,
        animationSpec = tween(durationMillis = 1000),
        label = ""
    )
    val heartRateOffset = remember(heartRate) {
        (heartRate / totalHeartRateRange) * screenWidth.value
    }
    var state by remember { mutableFloatStateOf(.0f) }

    LaunchedEffect(key1 = Unit, block = {
        delay(1000L)
        state = 1f
        pointerOffset = heartRateOffset
    })
    Box {
        Card(
            modifier = Modifier
                .height(52.dp)
                .padding(vertical = 20.dp)
                .fillMaxWidth(), shape = RoundedCornerShape(5.dp)
        ) {
            Row {
                slices.forEach {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(it.value),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(it.color),
                            contentAlignment = Alignment.Center
                        ) {
                        }
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .width(5.dp)
                .height(16.dp)
                .offset(x = animatedOffset.value.dp)
                .align(Alignment.CenterStart),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(1.dp, GrayAE),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {}
    }
}


@Preview(showBackground = true)
@Composable
private fun MeasuringResultPreview() {
    MeasuringResultScreenContent(
        modifier = Modifier.fillMaxSize(),
        measuringResults = HeartRateResultEntity(1719498620, 80),
        onDoneClicked = {},
        onHistoryClicked = {}
    )
}