package com.example.kommunalkaapp.data

import com.example.kommunalkaapp.db.KommunalkaDatabase
import com.example.kommunalkaapp.model.PaymentModel

internal class DatabaseClient(databaseDriverFactory: DatabaseDriverFactory) {
    private var dbRef = KommunalkaDatabase(databaseDriverFactory.createDriver())
    private var dbQuery = dbRef.kommunalkaDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllPayments()
        }
    }

    internal fun getAllPayments(): List<PaymentModel> {
        return  dbQuery.selectAll(::mapPayments).executeAsList()
    }

    internal fun createPayments(payments: List<PaymentModel>) {
        dbQuery.transaction {
            payments.forEach { paymentModel ->
                insertPayment(paymentModel)
            }
        }
    }

    private fun mapPayments(
        id: String,
        hotWaterCount: Long,
        coldWaterCount: Long,
        electricity: Long,
        date: String
    ): PaymentModel {
        return PaymentModel(
            id = id,
            hotWaterCount = hotWaterCount.toInt(),
            coldWaterCount = coldWaterCount.toInt(),
            electricity = electricity.toInt(),
            date = date
        )
    }

    private fun insertPayment(paymentModel: PaymentModel) {
        dbQuery.insertPayment(
            id = paymentModel.id,
            hotWaterCount = paymentModel.hotWaterCount.toLong(),
            coldWaterCount = paymentModel.coldWaterCount.toLong(),
            electricity = paymentModel.electricity.toLong(),
            date = paymentModel.date
        )
    }
}