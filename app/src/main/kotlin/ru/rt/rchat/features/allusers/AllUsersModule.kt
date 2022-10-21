package ru.rt.rchat.features.allusers

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.rt.rchat.di.ioDispatcherName
import ru.rt.rchat.features.allusers.data.remote.AllUsersRepositoryImpl
import ru.rt.rchat.features.allusers.domain.remote.AllUsersRepository
import ru.rt.rchat.features.allusers.domain.usecase.BanUserUseCase
import ru.rt.rchat.features.allusers.domain.usecase.LoadOnlineUsersUseCase
import ru.rt.rchat.features.allusers.presentation.UserViewModel

val allUsersModule = module {
    viewModel {
        UserViewModel(
            loadOnlineUsersUseCase = get(),
            banUserUseCase = get(),
            ioDispatcher = get(named(ioDispatcherName))
        )
    }
    factory { BanUserUseCase(allUsersRepository = get(), authRepository = get()) }
    factory { LoadOnlineUsersUseCase(allUsersRepository = get()) }
    single<AllUsersRepository> {
        AllUsersRepositoryImpl(
            dataSource = get(),
            ioDispatcher = get(named(ioDispatcherName))
        )
    }
}