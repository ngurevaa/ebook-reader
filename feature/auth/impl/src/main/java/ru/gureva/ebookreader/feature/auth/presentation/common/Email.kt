package ru.gureva.ebookreader.feature.auth.presentation.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction
import ru.gureva.ebookreader.core.designsystem.component.CustomTextField

@Composable
internal fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit
) {
    CustomTextField(
        value = email,
        onValueChange = { onEmailChange(it) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        )
    )
}
