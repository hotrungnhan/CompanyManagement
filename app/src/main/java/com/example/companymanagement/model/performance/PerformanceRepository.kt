package com.example.companymanagement.model.performance

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.companymanagement.model.ranking.RankerModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class PerformanceRepository (var col: CollectionReference){

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MMMM")
    suspend fun getPerformanceDoc(uuid: String, month : YearMonth): PerformanceModel? {

        val ref = col.document(uuid)
            .collection(month.format(formatter)).document("performance_sumary")
        if(ref.get().await().exists())
            return ref.get().await().toObject(PerformanceModel::class.java)
        else{
            var dummy = PerformanceModel(0, 0, 0, 0, 0, 0 )
            dummy.uid = "month_sumary"
            return dummy
        }
    }


    suspend fun updateDoc(uuid : String, month : YearMonth, performance : PerformanceModel) {
        col.document(uuid)
            .collection(month.format(formatter)).document("performance_sumary")
            .set(performance).await()

        //ToDo : get user position from userinfo

        var matchRanker = RankerModel(uuid, uuid, performance.computeTotalPoint())

        FirebaseFirestore.getInstance()
            .collection("ranking")
            .document(uuid).collection("ranking_" + month.format(formatter))
            .document("ranker_info")
            .set(matchRanker)
    }


}