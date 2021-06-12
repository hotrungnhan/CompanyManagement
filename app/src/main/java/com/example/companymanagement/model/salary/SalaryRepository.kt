package com.example.companymanagement.model.salary

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.CollectionReference
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
@RequiresApi(Build.VERSION_CODES.O)
class SalaryRepository (var col: CollectionReference) {

    suspend fun getSalaryDoc(uuid: String, year : String, month: String): SalaryModel? {
        val ref = col.document(uuid)
            .collection("yearlist").document(year)
            .collection("monthlist").document(month)
        if(ref.get().await().exists())
            return ref.get().await().toObject(SalaryModel::class.java)
        else{
            var dummy = SalaryModel(0, 0, 0, 0, 0, 0, 0, 0 )
            dummy.uid = month
            return dummy
        }
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