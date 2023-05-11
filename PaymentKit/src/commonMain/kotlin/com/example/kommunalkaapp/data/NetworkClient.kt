package com.example.kommunalkaapp.data

import com.example.kommunalkaapp.model.PaymentModel
import com.example.kommunalkaapp.model.PaymentPatchDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

expect fun createHttpClient(): HttpClient

internal class NetworkClient {
    private val httpClient = com.example.kommunalkaapp.data.createHttpClient()

    companion object URLS {
        const val localhost = "http://10.0.2.2:8080"
        const val paymentRoute = "/api/v1/payments"
    }

    suspend fun getPayments(): List<PaymentModel> {
        return httpClient.get("$localhost$paymentRoute").body()
    }

    suspend fun createPayment(paymentModel: PaymentModel): PaymentModel {
        return httpClient.post("$localhost$paymentRoute") {
            contentType(ContentType.Application.Json)
            setBody(paymentModel)
        }.body()
    }

    suspend fun updatePayment(patchDTO: PaymentPatchDTO): PaymentModel {
        return httpClient.patch("$localhost$paymentRoute") {
            contentType(ContentType.Application.Json)
            setBody(patchDTO)
        }.body()
    }

    suspend fun deletePayment(paymentId: String): Boolean {
        val responseStatusCode = httpClient.delete("$localhost$paymentRoute/${paymentId}") {
            contentType(ContentType.Application.Json)
        }.status
        return responseStatusCode == HttpStatusCode.OK
    }
}