package ua.oshevchuk.heartratetestapp.ui.screens.history

import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.oshevchuk.heartratetestapp.R
import ua.oshevchuk.heartratetestapp.common.Response
import ua.oshevchuk.heartratetestapp.ui.components.DotLoader
import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity
import ua.oshevchuk.heartratetestapp.ui.theme.RedFF

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val clearAllState = viewModel.clearAllState.collectAsState().value
    val getAllHeartResultsState = viewModel.resultsState.collectAsState().value
    viewModel.getAllResults()
    HistoryScreenContent(
        modifier = modifier,
        onBackClicked = onBackClicked,
        onClearHistoryClicked = {
            viewModel.clearAll()
        },
        clearAllState = clearAllState,
        getAllHeartResultsState = getAllHeartResultsState
    )

}

@Composable
fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onClearHistoryClicked: () -> Unit,
    clearAllState: Response<Unit>?,
    getAllHeartResultsState: Response<List<HeartRateResultEntity?>?>?
) {
    val context = LocalContext.current
    val errorText = stringResource(id = R.string.smth_went_wrong)
    val results = remember { mutableStateOf<List<HeartRateResultEntity?>?>(null) }
    when (getAllHeartResultsState) {
        is Response.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                DotLoader(modifier = Modifier.height(30.dp))
            }
        }

        is Response.Success -> {
            results.value = getAllHeartResultsState.data?.reversed()
        }

        is Response.Error -> {
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    errorText,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        else -> {}
    }

    if (clearAllState is Response.Success) {
        LaunchedEffect(Unit) {
            Toast.makeText(
                context,
                errorText,
                Toast.LENGTH_SHORT
            ).show()
            onBackClicked()
        }
    }

    if (clearAllState is Response.Error) {
        LaunchedEffect(Unit) {
            Toast.makeText(
                context,
                errorText,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold { paddingValues ->
        Box(modifier = modifier.padding(paddingValues)) {
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
                results.value?.let { list ->
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(list) { item ->
                            item?.let {
                                HistoryItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.dp),
                                    item = it
                                )
                            }
                        }
                    }
                }

            }
            FloatingActionButton(
                onClick = onClearHistoryClicked,
                shape = RoundedCornerShape(25.dp),
                containerColor = RedFF,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 31.dp, vertical = 15.dp)
                    .align(Alignment.BottomCenter)
            ) {
                if (clearAllState is Response.Loading)
                    DotLoader(modifier = Modifier.height(15.dp))
                else
                    Text(
                        text = stringResource(id = R.string.clear_history),
                        color = Color.White,
                        fontSize = 19.sp
                    )
            }
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
        onClearHistoryClicked = {},
        getAllHeartResultsState = Response.Success(
            listOf(
                HeartRateResultEntity(
                    timestamp = 0,
                    heartRate = 0
                )
            )
        ),
        clearAllState = Response.Loading()
    )
}