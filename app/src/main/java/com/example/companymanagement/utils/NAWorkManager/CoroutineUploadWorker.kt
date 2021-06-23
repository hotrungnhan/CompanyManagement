package com.example.companymanagement.utils.NAWorkManager


import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.companymanagement.model.performance.PerformanceModel
import com.example.companymanagement.model.ranking.RankerModel
import com.example.companymanagement.utils.VNeseDateConverter
import com.example.companymanagement.viewcontroller.fragment.leaderboard.RankerViewModel
import com.example.companymanagement.viewcontroller.fragment.salary.SalaryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.util.*

const val USER_UUID = "USER_UUID"
const val PERFORM_YEAR = "PERFORM_YEAR"
const val PERFORM_MONTH = "PERFORM_MONTH"
const val BASIC_POINT = "BASIC_POINT"
const val ABSENCE_A = "ABSENCE_A"
const val ABSENCE_NA = "ABSENCE_NA"
const val LATE_FAULT = "LATE_FAULT"
const val MONTH_OVER_TIME = "MONTH_OVER_TIME"
const val TASK_DONE = "TASK_DONE"

const val KEY_RESULT = "result"

@RequiresApi(Build.VERSION_CODES.O)
class CoroutineUploadWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        return@withContext try {
            val user_uuid = inputData.getString(USER_UUID)
            val perform_year = inputData.getFloat(PERFORM_YEAR, 0F)
            val perform_month = inputData.getFloat(PERFORM_MONTH, 1f)
            val basic_point = inputData.getLong(BASIC_POINT, 0)
            val absence_a = inputData.getLong(ABSENCE_A, 0)
            val absence_na = inputData.getLong(ABSENCE_NA, 0)
            val late_fault = inputData.getLong(LATE_FAULT, 0)
            val month_over_time = inputData.getLong(MONTH_OVER_TIME, 0)
            val task_done = inputData.getLong(TASK_DONE, 0)

            val performanceModel = PerformanceModel(basic_point, absence_a, absence_na, late_fault, month_over_time, task_done)
            compileAndUpload(user_uuid!!, perform_year, perform_month, performanceModel)

            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }

    }


    private fun compileAndUpload(uuid : String, year : Float, month : Float, performanceModel: PerformanceModel) {
        Log.e("Upload", LocalTime.now().toString())
        var salarymodel = SalaryViewModel()
        val refinedMonth = YearMonth.of(year.toInt(), month.toInt())
        salarymodel.updatePerformance(uuid, refinedMonth, performanceModel)
        salarymodel.updateSalaryFromPerformance(uuid, refinedMonth.year.toString(), VNeseDateConverter.convertMonthFloatToString(month))

        val rankermodel = RankerViewModel()
        rankermodel.retrieveLeaderBoardIn(YearMonth.of(year.toInt(), month.toInt()))
    }


}
