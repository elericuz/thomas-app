package com.elericuz.metrodelima

import okhttp3.logging.HttpLoggingInterceptor

object Constants {
    const val BASE_URL = "https://metrolima.ericvalera.dev"
    const val LOGGER_TAG = "METROLIMA"
    val LOGGER_LEVEL = HttpLoggingInterceptor.Level.HEADERS
    const val PAGE_SIZE = 32
}