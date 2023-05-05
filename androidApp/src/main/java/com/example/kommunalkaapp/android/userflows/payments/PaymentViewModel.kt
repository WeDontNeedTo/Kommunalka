package com.example.kommunalkaapp.android.userflows.payments

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kommunalkaapp.core.PaymentManager
import com.example.kommunalkaapp.data.DatabaseDriverFactory
import com.example.kommunalkaapp.model.PaymentModel

import kotlinx.coroutines.launch
import java.util.*

class PaymentViewModel(appObj: Application): AndroidViewModel(appObj) {
    var paymentListState = MutableLiveData<List<PaymentModel>>(listOf())
    private val paymentManager = PaymentManager(DatabaseDriverFactory(appObj))

    init {
        fetchPayments()
    }

    private fun fetchPayments() {
        println("fetchPayments")
        viewModelScope.launch {
            paymentListState.value = listOf(
                PaymentModel(
                    id = UUID.randomUUID().toString(),
                    hotWaterCount = 100,
                    coldWaterCount = 100,
                    electricity = 100,
                    date = "27.02.2002"
                ),
                PaymentModel(
                    id = UUID.randomUUID().toString(),
                    hotWaterCount = 200,
                    coldWaterCount = 200,
                    electricity = 200,
                    date = "27.02.2032"
                ),
                PaymentModel(
                    id = UUID.randomUUID().toString(),
                    hotWaterCount = 1020,
                    coldWaterCount = 200,
                    electricity = 200,
                    date = "27.02.1002"
                ),
                PaymentModel(
                    id = UUID.randomUUID().toString(),
                    hotWaterCount = 120,
                    coldWaterCount = 200,
                    electricity = 150,
                    date = "27.02.2023"
                ),
                PaymentModel(
                    id = UUID.randomUUID().toString(),
                    hotWaterCount = 200,
                    coldWaterCount = 200,
                    electricity = 200,
                    date = "27.02.2032"
                ),
                PaymentModel(
                    id = UUID.randomUUID().toString(),
                    hotWaterCount = 1020,
                    coldWaterCount = 200,
                    electricity = 200,
                    date = "27.02.1002"
                ),
                PaymentModel(
                    id = UUID.randomUUID().toString(),
                    hotWaterCount = 120,
                    coldWaterCount = 200,
                    electricity = 150,
                    date = "27.02.2023"
                )
            )
        }
    }
}