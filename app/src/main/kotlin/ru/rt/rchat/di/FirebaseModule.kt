package ru.rt.rchat.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import org.koin.dsl.module
import ru.rt.rchat.firebase.FirebaseAuthSource
import ru.rt.rchat.firebase.FirebaseDataSource
import ru.rt.rchat.firebase.FirebaseReferenceConnectedObserver
import ru.rt.rchat.firebase.FirebaseStorageSource

val firebaseModule = module {
    single { FirebaseAuthSource(get()) }
    single { FirebaseDataSource(get()) }
    single { FirebaseStorageSource(get()) }
    single { FirebaseAuth.getInstance() }
    single { FirebaseDatabase.getInstance() }
    single { FirebaseStorage.getInstance() }
    single { FirebaseReferenceConnectedObserver(get()) }
}