package com.example.companymanagement.model.ranking

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.performance.PerformanceModel
import com.example.companymanagement.model.salary.SalaryModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class RankingRepository (var col: CollectionReference) {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MMMM")

    suspend fun loadLeaderBoardIn(month : YearMonth) : List<RankerModel?> {
        val db = FirebaseFirestore.getInstance()
        val fomatter = DateTimeFormatter.ofPattern("yyyy-MMMM")

        val snapshots = db.collectionGroup("ranking_" + month.format(fomatter))
            .orderBy("ranker_point",  Query.Direction.DESCENDING)
            .get().await()

        var list = listOf<RankerModel?>()

        if (snapshots != null) {
            list = snapshots.documents.map{
                it.toObject(RankerModel::class.java)
            }
        }
        for(i in 0 until 3) {
            list[i]?.uid = snapshots.documents[i].reference.parent.parent?.id
        }
        return list
    }

    suspend fun getRankerDoc(uuid : String, month : YearMonth) : RankerModel?
    {
        val ref = col.document(uuid)
            .collection("ranking_" + month.format(formatter))
            .document("ranker_info")

        if(ref.get().await().exists())
            return ref.get().await().toObject(RankerModel::class.java)
        else{
            var dummy = RankerModel("N/A", "N/A", 0 )

            return dummy
        }
    }


}