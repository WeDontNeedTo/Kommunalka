package com.example.kommunalkaapp.android.userflows.payments

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kommunalkaapp.core.PaymentManager
import com.example.kommunalkaapp.data.DatabaseDriverFactory
import com.example.kommunalkaapp.model.PaymentModel

import kotlinx.coroutines.launch

class PaymentViewModel(appObj: Application): AndroidViewModel(appObj) {
//    var paymentListState by mutableStateListOf<PaymentModel>()
//        private set
    private val paymentManager = PaymentManager(DatabaseDriverFactory(appObj))

    init {
        fetchPayments()
    }

    private fun fetchPayments() {
        viewModelScope.launch {
           kotlin.runCatching {
               paymentManager.getPayments(true)
           }.onSuccess {
               println("it -- $it")
           }
        }
    }
}