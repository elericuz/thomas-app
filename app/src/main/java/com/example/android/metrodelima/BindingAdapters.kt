package com.example.android.metrodelima

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.metrodelima.utils.Dates
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@BindingAdapter("amount")
fun bindAmount(textView: TextView, amount: Double?) {
    amount?.let {
        textView.text = "S/${String.format("%.2f", amount)}"
    }
}

@BindingAdapter("dateFormatted")
fun bindDate(textView: TextView, dateHour: String?) {
    dateHour?.let {
        val date = Instant.parse(dateHour)
        textView.text = Dates.getDateFromLong(date.toEpochMilli(), "dd/MM")
    }
}

@BindingAdapter("yearFormatted")
fun bindYear(textView: TextView, dateHour: String?) {
    dateHour?.let {
        val date = Instant.parse(dateHour)
        textView.text = Dates.getDateFromLong(date.toEpochMilli(), "yyyy")
    }
}

@BindingAdapter("hourFormatted")
fun bindHour(textView: TextView, dateHour: String?) {
    dateHour?.let {
        val date = Instant.parse(dateHour)
        textView.text = Dates.getTimeFromLong(date.toEpochMilli())
    }
}

@BindingAdapter("capitalized")
fun bindText(textView: TextView, text: String?) {
    text?.let {
        textView.text =
            it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}

@BindingAdapter("uppercase")
fun bindTextUpperCase(textView: TextView, text: String?) {
    text?.let {
        textView.text = it.uppercase()
    }
}