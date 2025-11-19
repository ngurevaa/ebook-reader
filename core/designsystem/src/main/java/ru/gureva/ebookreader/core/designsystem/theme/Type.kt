package ru.gureva.ebookreader.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.gureva.ebookreader.core.designsystem.R

val regular = FontFamily(
    Font(R.font.wix_made_for_text_regular, FontWeight.Normal, FontStyle.Normal)
)

val medium = FontFamily(
    Font(R.font.wix_made_for_text_medium, FontWeight.Normal, FontStyle.Normal)
)

val semiBold = FontFamily(
    Font(R.font.wix_made_for_text_semi_bold, FontWeight.Normal, FontStyle.Normal)
)

val baseline = Typography()
val Typography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = semiBold),
    displayMedium = baseline.displayMedium.copy(fontFamily = semiBold),
    displaySmall = baseline.displaySmall.copy(fontFamily = semiBold),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = semiBold),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = semiBold),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = semiBold),
    titleLarge = baseline.titleLarge.copy(fontFamily = medium),
    titleMedium = baseline.titleMedium.copy(fontFamily = medium),
    titleSmall = baseline.titleSmall.copy(fontFamily = medium),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = regular),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = regular),
    bodySmall = baseline.bodySmall.copy(fontFamily = regular),
    labelLarge = baseline.labelLarge.copy(fontFamily = regular),
    labelMedium = baseline.labelMedium.copy(fontFamily = regular),
    labelSmall = baseline.labelSmall.copy(fontFamily = regular),
)
