package com.example.companymanagement.utils

import java.text.SimpleDateFormat
import java.util.*

class DateParser {
    companion object {
        val simpleDateParser = SimpleDateFormat("dd/MM/yyyy")
        val simpleTimeParser = SimpleDateFormat("hh:mm:ss a")
        val simpleDateAndTimeParser = SimpleDateFormat("dd/MM/yyyy | hh:mm:ss a")
        fun Date.toHumanReadDate(): String {
            return simpleDateParser.format(this) // this here is date
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