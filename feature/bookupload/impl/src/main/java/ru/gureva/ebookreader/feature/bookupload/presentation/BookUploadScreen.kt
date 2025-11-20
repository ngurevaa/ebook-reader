package ru.gureva.ebookreader.feature.bookupload.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import ru.gureva.ebookreader.core.designsystem.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun BookUploadScreen(
    viewModel: BookUploadViewModel = koinViewModel()
) {
    val state by viewModel.collectAsState()
    val dispatch = viewModel::dispatch

    AppTheme {
        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                BookUploadScreenContent(state, dispatch)
            }
        }
    }
}

@Composable
internal fun BookUploadScreenContent(
    state: BookUploadState,
    dispatch: (BookUploadEvent) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Загрузка книги",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        GetContent(
            fileName = state.fileName,
            selectFile = { dispatch(BookUploadEvent.SelectFile(it)) }
        )
    }
}


@Composable
internal fun GetContent(
    fileName: String?,
    selectFile: (Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { selectFile(it) }
    }
    Button(
        onClick = { launcher.launch("text/plain") },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Выбрать файл",
            style = MaterialTheme.typography.titleMedium
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
    if (fileName != null) {
        Text(text = fileName)
    }

}
