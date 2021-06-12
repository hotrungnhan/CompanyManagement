package com.example.companymanagement.viewcontroller.fragment.performance

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)

class Performance (){
    private var uid : String
    private var monthOfPayment : YearMonth

    private var taskDone : Int
    private var monthOverTime : Float

    private var absenceA : Int
    private var absenceNA : Int
    private var late : Int
    private var ruleAgainst : Int

    private var totalPoint : Int

    constructor(_uid : String) : this() {
        uid = _uid
    }
    constructor(_uid: String, _monthOfPayment : YearMonth) : this() {
        uid = _uid
        monthOfPayment = _monthOfPayment
    }
    init {
        uid = "N/A"
        monthOfPayment = YearMonth.now()

        absenceA = 0
        absenceNA = 0
        late = 0
        monthOverTime = 0F
        ruleAgainst = 0
        taskDone = 0

        totalPoint = 0
    }
    fun updateDetail(_absenceA : Int, _absenceNA : Int, _late : Int, _monthOverTime : Float, _ruleAgainst : Int, _taskDone : Int){
        absenceA = _absenceA
        absenceNA = _absenceNA
        late = _late
        monthOverTime = _monthOverTime
        ruleAgainst = _ruleAgainst
        taskDone = _taskDone

        computeTotalPoint()
    }

    private fun computePlus() = monthOfPayment.lengthOfMonth() * 10 + monthOverTime * 100 + taskDone * 5
    private fun computeMinus() = absenceA * 5 + absenceNA * 15 + late * 2 + ruleAgainst * 2
    fun computeTotalPoint() = computePlus() - computeMinus()

    fun getUID() = uid
    fun getMonthOfPayment() = monthOfPayment

    fun getTaskDone() = taskDone
    fun getMonOverTime() = monthOverTime

    fun getAbsenceA() = absenceA
    fun getAbsenceNA() = absenceNA
    fun getLate() = late
    fun getRuleAgainst() = late

    fun getTotalPoint() = totalPoint

    fun sendToFirebase(){
        //TODO
    }
}