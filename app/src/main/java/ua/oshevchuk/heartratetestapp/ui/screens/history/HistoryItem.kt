package ua.oshevchuk.heartratetestapp.ui.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity
import ua.oshevchuk.heartratetestapp.ui.theme.RedFF

@Composable
fun HistoryItem(modifier: Modifier = Modifier, item: HeartRateResultEntity) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.getPrettyHeartRate(),
                fontSize = 30.sp,
                color = Color.Black
            )
            Card(
                modifier = Modifier
                    .height(50.dp)
                    .width(2.dp),
                colors = CardDefaults.cardColors(containerColor = RedFF),
                shape = RoundedCornerShape(5.dp)
            ) {}
            Text(
                text = "${item.getTime()}\n${item.getDate()}",
                fontSize = 24.sp,
                color = Color.Black
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryItemPreview() {
    HistoryItem(
        Modifier
            .fillMaxWidth()
            .padding(15.dp),
        item = HeartRateResultEntity(1719434794L, 66)
    )
}