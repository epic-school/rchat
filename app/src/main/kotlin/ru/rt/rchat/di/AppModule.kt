package ru.rt.rchat.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.rt.rchat.features.allusers.data.remote.AllUsersRepositoryImpl
import ru.rt.rchat.features.autorization.data.remote.AuthRepository
import ru.rt.rchat.features.chat.data.remote.ChatRepository
import ru.rt.rchat.features.chatlist.data.remote.ChatListRepository
import ru.rt.rchat.features.newfriends.data.remote.NewFriendsRepository
import ru.rt.rchat.features.profile.data.remote.ProfileRepository

val appModule = module {
    single { AllUsersRepositoryImpl(get(), get(named(ioDispatcherName))) }
    single { AuthRepository(get(), get(named(ioDispatcherName))) }
    single { ChatRepository(get(), get(named(ioDispatcherName))) }
    single { ChatListRepository(get(), get(named(ioDispatcherName))) }
    single { NewFriendsRepository(get(), get(named(ioDispatcherName))) }
    single { ProfileRepository(get(), get(), get(named(ioDispatcherName))) }
}