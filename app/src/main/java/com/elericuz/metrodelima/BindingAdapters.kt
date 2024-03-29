package com.elericuz.metrodelima

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.elericuz.metrodelima.utils.Dates
import java.time.Instant
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

@BindingAdapter("colorTransaction")
fun bindColorText(textView: TextView, text: String?) {
    text?.let {
        if (it.lowercase().trim() == "uso") {
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.cancelTextColor))
        } else {
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.primaryTextColor))
        }
    }
}
