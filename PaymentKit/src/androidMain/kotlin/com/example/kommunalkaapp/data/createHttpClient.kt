package com.example.kommunalkaapp.data

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal fun AndroidHttpClient() = HttpClient(Android) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    engine {
        requestConfig.apply {
            connectTimeout = 5
        }
    }
}

actual fun createHttpClient(): HttpClient {
    return AndroidHttpClient()
}