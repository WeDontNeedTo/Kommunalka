package com.example.kommunalkaapp.core

import com.example.kommunalkaapp.data.DatabaseClient
import com.example.kommunalkaapp.data.DatabaseDriverFactory
import com.example.kommunalkaapp.data.NetworkClient
import com.example.kommunalkaapp.model.PaymentModel
import com.example.kommunalkaapp.model.PaymentPatchDTO
import com.example.kommunalkaapp.model.Tarrif

class PaymentManager(databaseDriverFactory: DatabaseDriverFactory) {
    private val networkClient = NetworkClient()
    private val databaseClient = DatabaseClient(databaseDriverFactory)

    @Throws(Exception::class)
    suspend fun getPayments(forceReload: Boolean): List<PaymentModel> {
        val cachedPayments = databaseClient.getAllPayments()
        return if (cachedPayments.isNotEmpty() && !forceReload) {
            cachedPayments
        } else {
            networkClient.getPayments().also {
                databaseClient.clearDatabase()
                databaseClient.createPayments(it)
            }
        }
    }

    @Throws(Exception::class)
    suspend fun createPayment(paymentModel: PaymentModel): PaymentModel {
        return networkClient.createPayment(paymentModel)
    }

    @Throws(Exception::class)
    suspend fun updatePayment(patchDTO: PaymentPatchDTO): PaymentModel {
        return networkClient.updatePayment(patchDTO)
    }

    @Throws(Exception::class)
    suspend fun deletePayment(paymentId: String): Boolean {
        return networkClient.deletePayment(paymentId)
    }

    fun calculate(currentMonthPaymentModel: PaymentModel, previousMonthPaymentModel: PaymentModel): Double {
        val diffPaymentModel = PaymentModel(
            id = currentMonthPaymentModel.id,
            hotWaterCount = currentMonthPaymentModel.hotWaterCount - previousMonthPaymentModel.hotWaterCount,
            coldWaterCount = currentMonthPaymentModel.coldWaterCount - previousMonthPaymentModel.coldWaterCount,
            electricity = currentMonthPaymentModel.electricity - previousMonthPaymentModel.electricity,
            date = currentMonthPaymentModel.date
        )
        val hotWaterCost = Tarrif.HOT_WATER_PRICE * diffPaymentModel.hotWaterCount
        val coldWaterCost = Tarrif.COLD_WATER_PRICE * diffPaymentModel.coldWaterCount
        val electricityCost = Tarrif.ELECTRICITY_PRICE * diffPaymentModel.electricity

        return  (hotWaterCost + coldWaterCost + electricityCost)
    }
}