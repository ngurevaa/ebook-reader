package ru.gureva.ebookreader.feature.auth.presentation.common

import androidx.compose.foundation.focusable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import ru.gureva.ebookreader.core.designsystem.component.CustomTextField

@Composable
internal fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    CustomTextField(
        value = email,
        onValueChange = { onEmailChange(it) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusable()
    )
}
