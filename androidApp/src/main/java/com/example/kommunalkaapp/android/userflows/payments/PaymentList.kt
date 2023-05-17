package com.example.kommunalkaapp.android.userflows.payments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kommunalkaapp.android.navigation.PaymentScreens
import com.example.kommunalkaapp.android.ui.OpenSans
import com.example.kommunalkaapp.model.PaymentModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PaymentList(viewModel: PaymentViewModel) {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()

    // TODO - add pull to refresh
    // TODO - add delete
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 8.dp,
                backgroundColor = Color.Black
            ) {
                Text("Платежи", color = Color.White, fontSize = 22.sp, modifier = Modifier.padding(horizontal = 4.dp))
                Spacer(modifier = Modifier.weight(1f, true))
            }
        },
        contentColor = Color.Black
    ) { contentPadding ->
        systemUiController.setSystemBarsColor(Color.Black)
        NavHost(
            navController = navController,
            startDestination = PaymentScreens.Start.name,
            modifier = Modifier.padding(contentPadding)
        ) {
            composable(PaymentScreens.Start.name) {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.padding(4.dp)
                ) {
                    item {
                        Row() {
                            Spacer(modifier = Modifier.weight(1f, true))
                            IconButton(onClick = {
                                navController.navigate(PaymentScreens.AddPayment.name)
                            }) {
                                Row() {
                                    Text("Добавьте новую запись!",
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )
                                    Icon(Icons.Filled.Add, contentDescription = "Добавить" )
                                }
                            }
                        }
                    }
                    item { PaymentInfoCard() }
                    items(viewModel.paymentListState.value ?: listOf()) { payment ->
                        PaymentRow(paymentModel = payment)
                    }
                }
            }

            composable(PaymentScreens.AddPayment.name) {
                AddPaymentScreen(viewModel, navController)
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
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = "Отчет за ${paymentModel.date}",
                color = Color.Black,
                style = TextStyle(
                    fontFamily = OpenSans,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Горячая вода: ${paymentModel.hotWaterCount} куб/м",
                    color = Color(0xFFFF0000),
                    style = TextStyle(
                        fontFamily = OpenSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = "Холодная вода: ${paymentModel.coldWaterCount} куб/м",
                    color = Color(0xFF1E90FF),
                    style = TextStyle(
                        fontFamily = OpenSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = "Электричество: ${paymentModel.electricity} кВт/ч",
                    color = Color(0xFFFF8C00),
                    style = TextStyle(
                        fontFamily = OpenSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}