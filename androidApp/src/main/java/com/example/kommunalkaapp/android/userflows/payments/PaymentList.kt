package com.example.kommunalkaapp.android.userflows.payments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kommunalkaapp.model.PaymentModel

@Composable
fun PaymentList(payments: List<PaymentModel>) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
        items(payments) { payment ->
            PaymentRow(paymentModel = payment)
        }
    }
}

@Composable
fun PaymentRow(paymentModel: PaymentModel) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Отчет за ${paymentModel.date}",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Text(
            text = "Горячая вода: ${paymentModel.hotWaterCount} куб/м",
            color = Color.Red
        )

        Text(
            text = "Холодная вода: ${paymentModel.coldWaterCount} куб/м",
            color = Color.Blue
        )

        Text(
            text = "Электричество: ${paymentModel.electricity} кВт/ч",
            color = Color.Yellow
        )
    }
}

@Preview
@Composable
fun PreviewPaymentRow() {
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