package com.example.companymanagement.utils.NAWorkManager

import android.os.Build
import android.provider.ContactsContract
import androidx.annotation.RequiresApi
import androidx.work.Data
import androidx.work.workDataOf
import com.example.companymanagement.model.performance.PerformanceModel
import java.time.YearMonth
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)

class DataConverted {
    companion object {
        fun convertPerformanceModelToData(uuid: String, year : Float, month : Float, performanceModel: PerformanceModel) : Data {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MMMM")
            var result : Data
            result = workDataOf(
                "USER_UUID" to uuid,
                "PERFORM_YEAR" to year,
                "PERFORM_MONTH" to month,
                "ABSENCE_A" to performanceModel.AbsenceA,
                "ABSENCE_NA" to performanceModel.AbsenceNA,
                "LATE_FAULT" to performanceModel.Late,
                "TASK_DONE" to performanceModel.TaskDone
            )
            return result
        }
    }
}