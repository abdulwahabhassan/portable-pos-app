package com.bankly.core.designsystem.component

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.bankly.core.designsystem.R
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import java.util.Calendar
import java.util.Date

@Composable
fun BanklyDatePicker(
    context: Context,
    onDateSelected: (LocalDate) -> Unit,
    onDismissDatePicker: () -> Unit,
) {
    val mCalendar = Calendar.getInstance()
    val year = mCalendar.get(Calendar.YEAR)
    val month = mCalendar.get(Calendar.MONTH)
    val day = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        context,
        R.style.Theme_Bankly_Light_DatePickerDialog,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val actualMonth = mMonth + 1
            val twoDigitIntDayOfMonth = if (mDayOfMonth < 10) "0$mDayOfMonth" else "$mDayOfMonth"
            val twoDigitIntMonth = if (actualMonth < 10) "0$actualMonth" else "$actualMonth"
            onDateSelected("$mYear-$twoDigitIntMonth-$twoDigitIntDayOfMonth".toLocalDate())
        },
        year,
        month,
        day,
    )
    mDatePickerDialog.setOnDismissListener {
        onDismissDatePicker()
    }
    mDatePickerDialog.show()
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
fun BanklyDatePickerPreview() {
    BanklyTheme {
        BanklyDatePicker(
            context = LocalContext.current,
            onDateSelected = {},
            onDismissDatePicker = {},
        )
    }
}
