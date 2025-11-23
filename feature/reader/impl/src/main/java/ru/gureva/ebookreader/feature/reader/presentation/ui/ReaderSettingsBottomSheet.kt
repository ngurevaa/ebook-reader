package ru.gureva.ebookreader.feature.reader.presentation.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.gureva.ebookreader.core.ui.noRippleClickable
import ru.gureva.ebookreader.feature.reader.presentation.ReaderEvent
import ru.gureva.ebookreader.feature.reader.presentation.ReaderEvent.ChangeFontSize
import ru.gureva.ebookreader.feature.reader.presentation.ReaderEvent.ChangeReadingTheme
import ru.gureva.ebookreader.feature.reader.presentation.ReaderEvent.CloseSettings
import ru.gureva.ebookreader.feature.reader.presentation.ReaderState
import ru.gureva.ebookreader.feature.reader.presentation.ReadingTheme
import ru.gureva.ebookreader.feature.reader.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsBottomSheet(
    state: ReaderState,
    dispatch: (ReaderEvent) -> Unit,
    scrollState: ScrollState
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { dispatch(CloseSettings) },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            FontSizeSettings(
                currentSize = state.fontSize,
                onSizeChange = { dispatch(ChangeFontSize(it)) }
            )
            Spacer(modifier = Modifier.height(32.dp))
            ThemeSettings(
                currentTheme = state.theme,
                onThemeChange = { dispatch(ChangeReadingTheme(it)) }
            )
            Spacer(modifier = Modifier.height(32.dp))
            ReadingProgressBar(
                progress = if (scrollState.maxValue > 0) {
                    scrollState.value.toFloat() / scrollState.maxValue
                } else 0f
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ReadingProgressBar(
    progress: Float
) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "${(progress * 100).toInt()}% ${stringResource(R.string.read)}",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
internal fun FontSizeSettings(
    currentSize: TextUnit,
    onSizeChange: (TextUnit) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.font_size),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            val fontSizes = listOf(16.sp, 18.sp, 20.sp)

            fontSizes.forEach { size ->
                val isSelected = size == currentSize

                Text(
                    text = stringResource(R.string.settings),
                    fontSize = size,
                    textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None,
                    modifier = Modifier.noRippleClickable { onSizeChange(size) },
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
internal fun ThemeSettings(
    currentTheme: ReadingTheme,
    onThemeChange: (ReadingTheme) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.theme),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            ReadingTheme.entries.forEach { theme ->
                Box(
                    modifier = Modifier
                        .size(height = 36.dp, width = 52.dp)
                        .background(
                            color = theme.backgroundColor,
                            shape = CircleShape
                        )
                        .border(
                            width = if (currentTheme == theme) 3.dp else 1.dp,
                            color = if (currentTheme == theme) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.secondary,
                            shape = CircleShape
                        )
                        .noRippleClickable { onThemeChange(theme) }
                )
            }
        }
    }
}
