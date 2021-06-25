package com.example.companymanagement.viewcontroller.activity.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.companymanagement.R
import com.example.companymanagement.model.performance.PerformanceModel
import com.example.companymanagement.utils.NAWorkManager.CoroutineUploadWorker
import com.example.companymanagement.utils.NAWorkManager.DataConverted
import com.example.companymanagement.utils.VNeseDateConverter
import com.example.companymanagement.viewcontroller.activity.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*


import java.util.concurrent.TimeUnit;

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();
        setContentView(R.layout.activity_main)
        auth.addAuthStateListener { p0: FirebaseAuth ->
            Log.d("User", p0.currentUser.toString());
            if (p0.currentUser == null) {
                val usernull = Intent(this, LoginActivity::class.java)
                startActivity(usernull)
            }
        }
    }

    private fun refreshPeriodicWork(context: Context) {

        val currentDate = Calendar.getInstance(Locale("vi", "VN"))
        val dueDate = Calendar.getInstance(Locale("vi", "VN"))

        // Set Execution on first run at the end of month
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
            30, TimeUnit.DAYS)
            .setInitialDelay(dayDiff, TimeUnit.MINUTES)
            .setInputData(
                DataConverted.convertPerformanceModelToData(
                    auth.currentUser?.uid!!.toString(), 2021F, 7F, generateDummy2()))
            .setConstraints(myConstraints)
            .addTag("myWorkManager")
            .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork("myWorkManager",
            ExistingPeriodicWorkPolicy.REPLACE, refreshCpnWork)

    }

    fun generateDummy2() : PerformanceModel{
        var dummy = PerformanceModel()
        dummy.AbsenceA = 0
        dummy.AbsenceNA = 1
        dummy.Late = 2
        dummy.TaskDone = 5
        return dummy
    }
}