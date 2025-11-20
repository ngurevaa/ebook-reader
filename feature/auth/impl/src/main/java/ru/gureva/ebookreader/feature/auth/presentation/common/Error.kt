package ru.gureva.ebookreader.feature.auth.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun Error(
    visible: Boolean,
    error: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        if (visible) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
