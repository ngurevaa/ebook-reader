package ru.gureva.ebookreader.feature.auth

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.gureva.ebookreader.feature.auth.presentation.login.LoginViewModel
import ru.gureva.ebookreader.feature.auth.usecase.CheckEmailValidityUseCase
import ru.gureva.ebookreader.feature.auth.usecase.CheckEmailValidityUseCaseImpl
import ru.gureva.ebookreader.feature.auth.usecase.CheckPasswordValidityUseCase
import ru.gureva.ebookreader.feature.auth.usecase.CheckPasswordValidityUseCaseImpl

val authModule = module {
    viewModel { LoginViewModel() }

    factory<CheckEmailValidityUseCase> { CheckEmailValidityUseCaseImpl(get()) }
    factory<CheckPasswordValidityUseCase> { CheckPasswordValidityUseCaseImpl(get()) }
}
