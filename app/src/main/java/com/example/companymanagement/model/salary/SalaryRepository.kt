package com.example.companymanagement.model.salary

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class SalaryRepository (private var col: CollectionReference) {

    suspend fun getSalaryDoc(uuid: String, year : Int, month: Int) : SalaryModel? {
        val startCal = Calendar.getInstance()
        startCal.set(year, month - 1, 1, 0, 0, 0)
        val start = startCal.time
        val endCal = Calendar.getInstance()
        endCal.set(year, month, 1, 0, 0, 0)
        val end = endCal.time
/*        Log.e("start", start.toString())
        Log.e("end", end.toString())*/
        val ref = col.whereEqualTo("owner_uuid", uuid)
            .whereGreaterThanOrEqualTo("create_time", start)
            .whereLessThan("create_time", end).get().await()

        if(ref.size() == 0)
        {
            val dummy = SalaryModel(uuid, 0, 0, 0, 0, 0, 0, 0 )
            dummy.CreateTime = start
            dummy.EndTime = end
            return dummy
        }
        else {
            Log.e("docid", ref.documents[0].id)
            return ref.documents[0].toObject(SalaryModel::class.java)
        }
    }
    suspend fun getListSalary(uuid : String, year : Int){
        val startCal = Calendar.getInstance()
        startCal.set(year, 0, 1, 0, 0, 0)
        val start = startCal.time
        val endCal = Calendar.getInstance()
        endCal.set(year + 1, 0, 1, 0, 0, 0)
        val end = endCal.time

        val list = col.whereEqualTo("owner_uuid", uuid)
            .whereGreaterThanOrEqualTo("create_time", start)
            .whereLessThan("create_time", end).get().await()
            .documents.map{
                it.toObject(SalaryModel::class.java)

        }

    }

    suspend fun getLastDoc(uuid : String) : SalaryModel? {
        val snapshots =
            col.orderBy("create_time", Query.Direction.DESCENDING)
                .limit(1).whereEqualTo("owner_uuid",uuid).get().await()
        return if(snapshots.size() != 0)
            snapshots.documents[0].toObject(SalaryModel::class.java)
        else
            null

    }
    suspend fun setDoc(salary: SalaryModel) {
        col.add(salary).await()
    }

    suspend fun updateDoc(docid: String, salary: SalaryModel){
        val new = hashMapOf(
            "rank_bonus" to salary.RankBonus,
            "checkin_fault_charge" to salary.CheckinFaultCharge,
            "task_bonus" to salary.TaskBonus

        )
        col.document(docid).set(new, SetOptions.merge()).await()
    }

    // no use for now
    suspend fun updateRankBonus(uuid : String, year: String, month : String, rankBonus : Long){
        val data = hashMapOf("rank_bonus" to rankBonus)
        col.document(uuid)
            .collection("yearlist").document(year)
            .collection("monthlist").document(month)
            .set(data, SetOptions.merge()).await()
    }

}