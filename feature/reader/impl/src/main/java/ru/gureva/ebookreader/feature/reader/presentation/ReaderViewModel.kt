package ru.gureva.ebookreader.feature.reader.presentation

import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.feature.reader.usecase.ReadFileUseCase

class ReaderViewModel : ContainerHost<ReaderState, ReaderSideEffect>, ViewModel(), KoinComponent {
    override val container = container<ReaderState, ReaderSideEffect>(ReaderState())

    private val readFileUseCase: ReadFileUseCase by inject()

    fun dispatch(event: ReaderEvent) {
        when (event) {
            is ReaderEvent.ReadFile -> readFile(event.fileName)
        }
    }

    private fun readFile(fileName: String) = intent {
        runCatching { readFileUseCase(fileName) }
            .onSuccess {
                reduce { state.copy(text = it) }
            }
            .onFailure {
                it.printStackTrace()
            }
    }
}
