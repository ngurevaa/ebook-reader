package ru.gureva.ebookreader.feature.auth

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.gureva.ebookreader.feature.auth.datasource.RemoteFirebaseDatasource
import ru.gureva.ebookreader.feature.auth.presentation.login.LoginViewModel
import ru.gureva.ebookreader.feature.auth.presentation.registration.RegistrationViewModel
import ru.gureva.ebookreader.feature.auth.repository.AuthRepository
import ru.gureva.ebookreader.feature.auth.repository.AuthRepositoryImpl
import ru.gureva.ebookreader.feature.auth.usecase.CheckEmailValidityUseCase
import ru.gureva.ebookreader.feature.auth.usecase.CheckEmailValidityUseCaseImpl
import ru.gureva.ebookreader.feature.auth.usecase.CheckPasswordValidityUseCase
import ru.gureva.ebookreader.feature.auth.usecase.CheckPasswordValidityUseCaseImpl
import ru.gureva.ebookreader.feature.auth.usecase.SignUpUseCase
import ru.gureva.ebookreader.feature.auth.usecase.SignUpUseCaseImpl

val authModule = module {
    viewModel { LoginViewModel() }
    viewModel { RegistrationViewModel() }

    factory<CheckEmailValidityUseCase> { CheckEmailValidityUseCaseImpl(get()) }
    factory<CheckPasswordValidityUseCase> { CheckPasswordValidityUseCaseImpl(get()) }
    factory<SignUpUseCase> { SignUpUseCaseImpl(get()) }

    factory<AuthRepository> { AuthRepositoryImpl(get()) }

    factory { RemoteFirebaseDatasource() }
}
