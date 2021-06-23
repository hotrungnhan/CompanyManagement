package com.example.companymanagement.utils.NAWorkManager

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.*
import com.example.companymanagement.utils.VNeseDateConverter
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*
import java.util.concurrent.TimeUnit
@RequiresApi(Build.VERSION_CODES.O)
object WorkManagerScheduler {


    fun refreshPeriodicWork(context: Context, lastUploadMonth : Int) {

        val currentDate = Calendar.getInstance(Locale("vi", "VN"))
        val dueDate = Calendar.getInstance(Locale("vi", "VN"))

        // Set Execution around 04:00:00 AM
        dueDate.set(Calendar.DAY_OF_MONTH, YearMonth.now().lengthOfMonth())
        dueDate.set(Calendar.MONTH, YearMonth.now().month.value - 1)
        dueDate.set(Calendar.YEAR, YearMonth.now().year)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.MONTH, 1)
        }

        val dayDiff = VNeseDateConverter.getDayDiff(currentDate, dueDate)



        Log.e("MyWorker", "time difference $dayDiff")

        //define constraints
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        val refreshCpnWork = PeriodicWorkRequest.Builder(CoroutineUploadWorker::class.java,
            15, TimeUnit.MINUTES)
            //.setInitialDelay(dayDiff, TimeUnit.DAYS)
            .setConstraints(myConstraints)
            .addTag("myWorkManager")
            .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork("myWorkManager",
            ExistingPeriodicWorkPolicy.REPLACE, refreshCpnWork)

    }
}