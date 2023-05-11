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
        viewModelScope.launch {
            paymentListState.value = paymentManager.getPayments(forceReload = true)
        }
    }

    fun createNewPayment(newPaymentModel: PaymentModel) {
        viewModelScope.launch {
            paymentManager.createPayment(newPaymentModel)
            paymentListState.value = paymentManager.getPayments(forceReload = true)
        }
    }
}