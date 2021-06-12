package com.example.companymanagement.model.salary

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.CollectionReference
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class SalaryRepository (var col: CollectionReference) {


    suspend fun addStaffDoc(uuid: String) {
        col.add(uuid).await();
    }


    suspend fun findUserDoc(uuid: String): SalaryModel? {
        ///stil editing
        return col.document(uuid).get().await().toObject(SalaryModel::class.java)
    }

    suspend fun getSalaryDoc(uuid: String, year : String, month: String): SalaryModel? {
        return col.document(uuid)
            .collection("yearlist").document(year)
            .collection("monthlist").document(month)
            .get().await().toObject(SalaryModel::class.java)
    }

    suspend fun updateDoc(uuid : String, year : String, month : String, salary: SalaryModel) {
        //cant be used yet
        col.document(uuid)
            .collection("yearlist").document(year)
            .collection("monthlist").document(month)
            .set(salary).await()
    }

    suspend fun deleteDoc(uuid: String, salary: SalaryModel) {
       // col.document(uuid).collection("monthlist").document(salary.MonthId!!).delete().await()
    }
}