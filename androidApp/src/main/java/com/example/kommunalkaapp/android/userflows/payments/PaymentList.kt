package com.example.kommunalkaapp.android.userflows.payments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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
fun PaymentList(viewModel: PaymentViewModel) {
    Scaffold(
        topBar = {
            TopAppBar() {
                Text("Платежи", fontSize = 22.sp)
            }
        }
    ) { contentPadding ->
        Box() {
            LazyColumn(
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                item {
                    PaymentInfoCard()
                }
                items(viewModel.paymentListState.value ?: listOf()) { payment ->
                    PaymentRow(paymentModel = payment)
                }
            }
        }
    }
}

@Composable
fun PaymentInfoCard() {
    Box() {
        Card() {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Сумма за прошлый месяц",
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )

                Text(
                    text = "Сумма за текущий месяц",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )

                Divider()

                Text(
                    text = "Текущий платеж: ",
                    color = Color.Green,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            }
        }
    }
}

@Composable
fun PaymentRow(paymentModel: PaymentModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 4.dp,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = "Отчет за ${paymentModel.date}",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Горячая вода: ${paymentModel.hotWaterCount} куб/м",
                    color = Color.Red,
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    text = "Холодная вода: ${paymentModel.coldWaterCount} куб/м",
                    color = Color.Blue,
                    fontWeight = FontWeight.SemiBold,
                    )

                Text(
                    text = "Электричество: ${paymentModel.electricity} кВт/ч",
                    color = Color.Cyan,
                    fontWeight = FontWeight.SemiBold,
                    )
            }
        }
    }
}