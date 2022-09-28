package ru.rt.rchat.di

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val ioDispatcherName = "IoDispatcher"
const val mainDispatcherName = "MainDispatcher"
const val exceptionHandlerName = "ExceptionHandler"

val dispatchers = module {
    single(named(ioDispatcherName)) { Dispatchers.IO }
    single(named(mainDispatcherName)) { Dispatchers.Main }
    single(named(exceptionHandlerName)) {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e("CoroutineExceptionHandler", "$coroutineContext", throwable)
        }
    }
}