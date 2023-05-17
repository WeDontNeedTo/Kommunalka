package com.example.kommunalkaapp.android.userflows.payments

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kommunalkaapp.android.navigation.PaymentScreens
import com.example.kommunalkaapp.android.ui.OpenSans
import com.example.kommunalkaapp.model.PaymentModel
import java.util.*

@Composable
fun AddPaymentScreen(
    paymentViewModel: PaymentViewModel,
    navHostController: NavHostController
) {
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // TODO - move datepicker logic to ViewModel?
    // TODO - change datepicker color
    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    val hotWater = remember{mutableStateOf("")}
    val coldWater = remember{mutableStateOf("")}
    val electricity = remember{mutableStateOf("")}

    Column() {
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .padding(4.dp)
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = true)
        ) {
            Row() {
                IconButton(onClick = {
                    navHostController.navigate(PaymentScreens.Start.name)
                },
                    Modifier.padding(0.dp)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                }
            }

            Text(
                text = "Добавьте запись о счетчиках",
                style = TextStyle(
                    fontFamily = OpenSans,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Выбранная дата: ${mDate.value}",
                style = TextStyle(
                    fontFamily = OpenSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                )
            )

            Button(onClick = {
                mDatePickerDialog.show()
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black) ) {
                Text(text = "Открыть календарь", color = Color.White)
            }

            Divider()

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text("Горячая вода: ${hotWater.value} куб/м3")
                    TextField(
                        value = hotWater.value,
                        singleLine = true,
                        onValueChange = { hotWater.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text("Холодная вода: ${coldWater.value} куб/м3")
                    TextField(
                        value = coldWater.value,
                        singleLine = true,
                        onValueChange = { coldWater.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text("Электроэнергия: ${electricity.value} кВт/ч")
                    TextField(
                        value = electricity.value,
                        singleLine = true,
                        onValueChange = { electricity.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Box() {
                    Button(onClick = {
                        // TODO - auto-pop screen
                        paymentViewModel.isCreatePaymentLoading.value = true
                        paymentViewModel.createNewPayment(
                            PaymentModel(
                                id = UUID.randomUUID().toString(),
                                hotWaterCount = hotWater.value.toInt(),
                                coldWaterCount = coldWater.value.toInt(),
                                electricity = electricity.value.toInt(),
                                date = mDate.value
                            )
                        )
                        navHostController.navigate(PaymentScreens.Start.name)
                    },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                        enabled = !paymentViewModel.isCreatePaymentLoading.value
                    ) {
                        Text(
                            text = "Добавить запись",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }

                    if (paymentViewModel.isCreatePaymentLoading.value) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color.White)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
