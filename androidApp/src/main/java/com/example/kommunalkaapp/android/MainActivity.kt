package com.example.kommunalkaapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.kommunalkaapp.android.userflows.payments.PaymentList
import com.example.kommunalkaapp.model.PaymentModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PaymentList(payments = listOf(
                    PaymentModel(
                        id = "GD67G12GS126SG126",
                        hotWaterCount = 699,
                        coldWaterCount = 700,
                        electricity = 5000,
                        date = "20.06.2022"
                    ),
                    PaymentModel(
                        id = "GD67G12GS126SG126",
                        hotWaterCount = 699,
                        coldWaterCount = 700,
                        electricity = 5000,
                        date = "22.06.2022"
                    )
                ))
            }
        }
    }
}

@Composable
fun MainView() {
    Text(text = "Hello!")
}
