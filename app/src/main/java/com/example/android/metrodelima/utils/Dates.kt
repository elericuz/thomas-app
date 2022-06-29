package com.example.android.metrodelima.utils

import android.annotation.SuppressLint
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class Dates {
    companion object {
        @SuppressLint("NewApi")
        fun getLocalDateTimeFromLong(date: Long): LocalDateTime = Instant
            .ofEpochMilli(date).atZone(
                TimeZone
                    .getTimeZone("UTC")
                    .toZoneId()
            ).toLocalDateTime()

        fun getLocalDateFromLong(date: Long): LocalDate? {
            return getLocalDateTimeFromLong(date).toLocalDate()
        }

        fun getLocalTimeFromLong(date: Long): LocalTime? {
            return getLocalDateTimeFromLong(date).toLocalTime()
        }

        fun getDateFromLong(date: Long, format: String = "dd-MM-yyyy"): String {
            val formatter = DateTimeFormatter.ofPattern(format, Locale.US)
            var localDate = getLocalDateFromLong(date)

            return localDate!!.format(formatter)
        }

        fun getTimeFromLong(date: Long): String {
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US)
            var localDate = getLocalTimeFromLong(date)

            return localDate!!.format(formatter)
        }
    }
}