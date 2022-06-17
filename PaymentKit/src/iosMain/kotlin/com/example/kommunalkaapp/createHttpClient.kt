package com.example.kommunalkaapp

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal fun IOSHttpClient() = HttpClient(Darwin) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}

actual fun createHttpClient(): HttpClient {
    return IOSHttpClient()
}