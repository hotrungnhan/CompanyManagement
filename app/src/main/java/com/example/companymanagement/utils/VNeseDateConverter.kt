package com.example.companymanagement.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class VNeseDateConverter {


    companion object {
        var input = LocalDate.now()
        var formatter = DateTimeFormatter.ofPattern("MMMM", Locale("vi", "VN"))
        fun convertMonthFloatToString(input : Float) : String {
            var output = ""
            when (input) {
                1f -> output = "JANUARY"
                2f -> output = "FEBRUARY"
                3f -> output = "MARCH"
                4f -> output = "APRIL"
                5f -> output = "MAY"
                6f -> output = "JUNE"
                7f -> output = "JULY"
                8f -> output = "AUGUST"
                9f -> output = "SEPTEMBER"
                10f -> output = "OCTOBER"
                11f -> output = "NOVEMBER"
                12f -> output = "DECEMBER"
            }
            return output
        }
        fun vnConvertMonth(month: String): String {
            var x: Int = 1
            when (month) {
                "JANUARY" -> x = 1
                "FEBRUARY" -> x = 2
                "MARCH" -> x = 3
                "APRIL" -> x = 4
                "MAY" -> x = 5
                "JUNE" -> x = 6
                "JULY" -> x = 7
                "AUGUST" -> x = 8
                "SEPTEMBER" -> x = 9
                "OCTOBER" -> x = 10
                "NOVEMBER" -> x = 11
                "DECEMBER" -> x = 12

                else -> { // Note the block
                    print("Invalid input")
                }
            }
            input = LocalDate.of(1, x, 1)
            return input.format(formatter)
        }
    }
}