package ua.oshevchuk.heartratetestapp.ui.constants

import androidx.compose.ui.graphics.Color
import ua.oshevchuk.heartratetestapp.R
import ua.oshevchuk.heartratetestapp.ui.theme.Blue21
import ua.oshevchuk.heartratetestapp.ui.theme.Green1C
import ua.oshevchuk.heartratetestapp.ui.theme.Red4C

enum class ResultTypes(val titleRes: Int, val color: Color, val rangeDesc : String) {
    SLOWED(R.string.slowed, Blue21, "<60 BPM"),
    NORMAL(R.string.normal, Green1C, "60-100 BPM"),
    HURRIED(R.string.hurried, Red4C, ">100 BPM")
}