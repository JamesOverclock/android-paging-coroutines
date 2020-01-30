package com.l3ios.androidpagingwithcoroutines.utils

import java.io.IOException
import java.lang.Exception

suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> =
    try {
        call.invoke()
    } catch (e: Exception) {
        Result.Error(IOException(errorMessage, e))
    }

val <T> T.exhaustive: T get() = this