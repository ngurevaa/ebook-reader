package ru.gureva.ebookreader.feature.profile

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.gureva.ebookreader.feature.profile.datasource.RemoteFirebaseDataSource
import ru.gureva.ebookreader.feature.profile.presentation.ProfileViewModel
import ru.gureva.ebookreader.feature.profile.repository.ProfileRepository
import ru.gureva.ebookreader.feature.profile.repository.ProfileRepositoryImpl
import ru.gureva.ebookreader.feature.profile.usecase.GetProfileDataUseCase
import ru.gureva.ebookreader.feature.profile.usecase.GetProfileDataUseCaseImpl
import ru.gureva.ebookreader.feature.profile.usecase.LogoutUseCase
import ru.gureva.ebookreader.feature.profile.usecase.LogoutUseCaseImpl
import ru.gureva.ebookreader.feature.profile.usecase.UpdateProfileDataUseCase
import ru.gureva.ebookreader.feature.profile.usecase.UpdateProfileDataUseCaseImpl

val profileModule = module {
    viewModel { ProfileViewModel() }

    factory<GetProfileDataUseCase> { GetProfileDataUseCaseImpl(get()) }
    factory<LogoutUseCase> { LogoutUseCaseImpl(get()) }
    factory<UpdateProfileDataUseCase> { UpdateProfileDataUseCaseImpl(get()) }

    factory<ProfileRepository> { ProfileRepositoryImpl(get()) }

    factory { RemoteFirebaseDataSource() }
}
