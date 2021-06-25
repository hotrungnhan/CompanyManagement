package com.example.companymanagement.model.salary

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import java.time.YearMonth
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
            var dummy = SalaryModel(uuid, 0, 0, 0, 0, 0, 0, 0 )
            dummy.uid = month

            return dummy
        }
    }
    /// ToDo : Complete this
    suspend fun getSalaryDoc(uuid: String, year : Float, month: Float): SalaryModel? {
        val start = Calendar.getInstance()
        start.set(year.toInt(), month.toInt(), 1)
        val end = Calendar.getInstance()
        end.set(year.toInt(), month.toInt() + 1, 1)
        val ref = col.whereGreaterThanOrEqualTo("creat_time", start).whereLessThan("create_time", end).get().await()

        return null
    }

    suspend fun updateDoc(uuid : String, salary: SalaryModel) {
        col.add(salary).await()
    }

    suspend fun updateRankBonus(uuid : String, year: String, month : String, rankBonus : Long){
        val data = hashMapOf("rank_bonus" to rankBonus)
        col.document(uuid)
            .collection("yearlist").document(year)
            .collection("monthlist").document(month)
            .set(data, SetOptions.merge()).await()
    }

}