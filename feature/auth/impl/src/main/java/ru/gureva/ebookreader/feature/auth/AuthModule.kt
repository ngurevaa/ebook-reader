package ru.gureva.ebookreader.feature.auth

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.gureva.ebookreader.feature.auth.presentation.login.LoginViewModel

val authModule = module {
    viewModel { LoginViewModel() }
}
