package ru.gureva.ebookreader.feature.reader.presentation

import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.core.util.ResourceManager
import ru.gureva.ebookreader.feature.reader.R
import ru.gureva.ebookreader.feature.reader.usecase.GetProgressUseCase
import ru.gureva.ebookreader.feature.reader.usecase.ReadFileUseCase
import ru.gureva.ebookreader.feature.reader.usecase.SaveProgressUseCase

class ReaderViewModel : ContainerHost<ReaderState, ReaderSideEffect>, ViewModel(), KoinComponent {
    override val container = container<ReaderState, ReaderSideEffect>(ReaderState())

    private val resourceManager: ResourceManager by inject()
    private val readFileUseCase: ReadFileUseCase by inject()
    private val saveProgressUseCase: SaveProgressUseCase by inject()
    private val getProgressUseCase: GetProgressUseCase by inject()

    fun dispatch(event: ReaderEvent) {
        when (event) {
            is ReaderEvent.ReadFile -> readFile(event.fileName, event.title)
            ReaderEvent.OpenSettings -> openSettings()
            ReaderEvent.CloseSettings -> closeSettings()
            is ReaderEvent.ChangeFontSize -> changeFontSize(event.size)
            is ReaderEvent.ChangeReadingTheme -> changeReadingTheme(event.theme)
            is ReaderEvent.SaveProgress -> saveProgress(event.progress)
            ReaderEvent.CloseReader -> closeReader()
        }
    }

    private fun closeReader() = intent {
        postSideEffect(ReaderSideEffect.NavigateBack)
    }

    private fun saveProgress(progress: Float) = intent {
        runCatching { saveProgressUseCase(state.fileName, progress) }
    }

    private fun changeReadingTheme(theme: ReadingTheme) = intent {
        reduce { state.copy(theme = theme) }
    }

    private fun changeFontSize(size: TextUnit) = intent {
        reduce { state.copy(fontSize = size) }
    }

    private fun closeSettings() = intent {
        reduce { state.copy(showSettings = false) }
    }

    private fun openSettings() = intent {
        reduce { state.copy(showSettings = true) }
    }

    private fun readFile(fileName: String, title: String) = intent {
        reduce { state.copy(fileName = fileName, title = title) }

        runCatching {
            readFileUseCase(fileName).collect { newPageText ->
                reduce { state.copy(text = state.text + newPageText) }
            }
        }
            .onSuccess {
                val progress = getProgressUseCase(state.fileName)
                reduce { state.copy(progress = progress) }
            }
            .onFailure {
                postSideEffect(ReaderSideEffect.ShowSnackbarWithRetryButton(
                    resourceManager.getString(R.string.file_loading_error)
                ))
        }
    }
}
