package ua.oshevchuk.heartratetestapp.ui.screens.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
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
import ua.oshevchuk.heartratetestapp.ui.theme.RedFF

@Composable
fun HistoryItem(modifier: Modifier = Modifier, item: HistoryEntity) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = item.getPrettyHeartRate(),
                modifier = Modifier.align(Alignment.CenterStart),
                fontSize = 36.sp,
                color = Color.Black
            )
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
                    .width(2.dp).align(Alignment.Center),
                colors = CardDefaults.cardColors(containerColor = RedFF),
                shape = RoundedCornerShape(5.dp)
            ){}
            Text(
                text = "${item.getTime()}\n${item.getDate()}",
                modifier = Modifier.align(Alignment.CenterEnd),
                fontSize = 24.sp,
                color = Color.Black
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryItemPreview() {
    HistoryItem(Modifier.fillMaxWidth().height(98.dp).padding(15.dp), item = HistoryEntity(1719434794L, 66))
}