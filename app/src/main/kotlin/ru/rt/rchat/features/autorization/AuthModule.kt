package ru.rt.rchat.features.autorization

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.rt.rchat.di.ioDispatcherName
import ru.rt.rchat.features.autorization.data.remote.AuthRepository
import ru.rt.rchat.features.autorization.presentation.AuthorizationViewModel

val authModule = module {
    viewModel { AuthorizationViewModel(authRepository = get(), ) }

    single { AuthRepository(authSource = get(), ioDispatcher = get(named(ioDispatcherName))) }
}