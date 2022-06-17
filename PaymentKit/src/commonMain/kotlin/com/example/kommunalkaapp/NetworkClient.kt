package com.example.kommunalkaapp

import com.example.kommunalkaapp.Model.PaymentModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

expect fun createHttpClient(): HttpClient

class NetworkClient {
    private val httpClient = createHttpClient()
    suspend fun getPayments(): List<PaymentModel> {
        return httpClient.get("http://0.0.0.0:8080/api/v1/payments").body()
    }
}