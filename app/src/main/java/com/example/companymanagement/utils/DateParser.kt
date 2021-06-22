package com.example.companymanagement.utils

import java.text.SimpleDateFormat
import java.util.*

class DateParser {
    companion object {
        var simpleDateParser = SimpleDateFormat("dd/MM/yyyy")
        fun Date.toHumanReadDate(): String {
            return simpleDateParser.format(this) // this here is date
        }

        fun parser(date: String): Date {
            var date = simpleDateParser.parse(date);
            if (date == null) {
                throw Exception("Date input error")
            } else return date;
        }
    }
}