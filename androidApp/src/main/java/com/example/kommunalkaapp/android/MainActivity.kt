package com.example.kommunalkaapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.kommunalkaapp.android.userflows.payments.PaymentList
import com.example.kommunalkaapp.android.userflows.payments.PaymentViewModel
import com.example.kommunalkaapp.model.PaymentModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PaymentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = PaymentViewModel(application)
        super.onCreate(savedInstanceState)
        setContent {
            PaymentList(viewModel = viewModel)
        }
    }
}
