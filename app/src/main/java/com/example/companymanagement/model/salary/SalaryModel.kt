package com.example.companymanagement.model.salary

import android.os.Build
import androidx.annotation.RequiresApi
import java.math.BigDecimal
import java.time.YearMonth

import androidx.annotation.Keep
import com.example.companymanagement.utils.VNeseDateConverter
import com.example.companymanagement.utils.VietnamDong
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import java.time.format.DateTimeFormatter

@Keep
@RequiresApi(Build.VERSION_CODES.O)

data class SalaryModel (
    @get: PropertyName("basic_salary")
    @set: PropertyName("basic_salary")
    var BasicSalary: Long = 0,
    @get: PropertyName("over_time_bonus")
    @set: PropertyName("over_time_bonus")
    var OverTimeBonus: Long = 0,
    @get: PropertyName("rank_bonus")
    @set: PropertyName("rank_bonus")
    var RankBonus: Long = 0,
    @get: PropertyName("checkin_fault_charge")
    @set: PropertyName("checkin_fault_charge")
    var CheckinFaultCharge: Long = 0,
    @get: PropertyName("task_bonus")
    @set: PropertyName("task_bonus")
    var TaskBonus: Long = 0,
    @get: PropertyName("tax_deduction")
    @set: PropertyName("tax_deduction")
    var TaxDeduction: Long = 0,
    @get: PropertyName("total_bonus")
    @set: PropertyName("total_bonus")
    var TotalBonus: Long = 0,
    @get: PropertyName("total_salary")
    @set: PropertyName("total_salary")
    var TotalSalary: Long = 0,){

    @DocumentId
    var uid: String? = null


    fun setBasicSalary(position : String) : VietnamDong {
        return if(position == "Manager")
            VietnamDong(BigDecimal(20000000))
        else
            VietnamDong(BigDecimal(10000000))
    }

    fun compute(month : YearMonth)
    {
        TaxDeduction = BasicSalary * month.lengthOfMonth() / 20
        TotalBonus = - CheckinFaultCharge + OverTimeBonus + RankBonus - TaxDeduction

        TotalSalary = BasicSalary * month.lengthOfMonth() + TotalBonus
    }

    fun compute(year : String, month : String)
    {
        val datetime = VNeseDateConverter.convertStringToYearMonth(year, month)

        TaxDeduction = BasicSalary * datetime.lengthOfMonth() / 20
        TotalBonus = - CheckinFaultCharge + OverTimeBonus + RankBonus - TaxDeduction

        TotalSalary = BasicSalary * datetime.lengthOfMonth() + TotalBonus
    }

}