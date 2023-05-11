package com.example.kommunalkaapp.android.userflows.payments

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
            Text(
                text = "Добавьте запись о счетчиках",
                style = TextStyle(
                    fontFamily = OpenSans,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Row() {
                // TODO - make more pretty design
                Button(onClick = {
                    navHostController.navigate(PaymentScreens.Start.name)
                }) {
                    Text(
                        text = "Назад",
                        style = TextStyle(
                            fontFamily = OpenSans,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 19.sp
                        )
                    )
                }
            }

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
                    TextField(value = hotWater.value, onValueChange = { hotWater.value = it })
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text("Холодная вода: ${coldWater.value} куб/м3")
                    TextField(value = coldWater.value, onValueChange = { coldWater.value = it })
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text("Электроэнергия: ${electricity.value} кВт/ч")
                    TextField(value = electricity.value, onValueChange = { electricity.value = it })
                }

                Button(onClick = {
                    // TODO - handle UI loading states
                    // TODO - auto-pop screen
                    paymentViewModel.createNewPayment(
                        PaymentModel(
                            id = UUID.randomUUID().toString(),
                            hotWaterCount = hotWater.value.toInt(),
                            coldWaterCount = coldWater.value.toInt(),
                            electricity = electricity.value.toInt(),
                            date = mDate.value
                        )
                    )
                },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                ) {
                    Text(text = "Добавить запись", color = Color.White)
                }
            }
        }
    }
}
