package com.example.companymanagement.utils

import android.util.Log
import com.example.companymanagement.utils.DateParser.Companion.toLocalDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class DateParser {
    companion object {
        val simpleDateParser = SimpleDateFormat("dd/MM/yyyy")
        val simpleTimeParser = SimpleDateFormat("hh:mm:ss a")
        val simpleDateAndTimeParser = SimpleDateFormat("dd/MM/yyyy | hh:mm:ss a")
        fun Date.toHumanReadDate(): String {
            return simpleDateParser.format(this) // this here is date
        }

        fun Date.toLocalDate(): LocalDate {
            Log.d("dateTo localDate",
                "$this ${LocalDate.of(this.year + 1900  , this.month + 1, this.date)}")
            return LocalDate.of(this.year + 1900, this.month + 1, this.date)

        }

        fun Date.toLocalDateTime(): LocalDateTime {
            return LocalDateTime.of(this.year + 1900,
                this.month + 1,
                this.date,
                this.hours,
                this.minutes,
                this.seconds)
        }

        fun Date.toHumanReadTime(): String {
            return simpleTimeParser.format(this) //
        }

        fun Date.toHumanDateAndTime(): String {
            return simpleDateAndTimeParser.format(this) //
        }

        fun parser(date: String): Date {
            var date = simpleDateParser.parse(date);
            if (date == null) {
                throw Exception("Date input error")
            } else return date;
        }
    }
}