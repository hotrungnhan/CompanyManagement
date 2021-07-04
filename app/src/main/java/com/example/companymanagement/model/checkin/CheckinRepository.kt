package com.example.companymanagement.model.checkin

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.format.DateTimeFormatter


class CheckinRepository(var col: CollectionReference) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getOntime(uuid: String): MutableList<CheckinModel> {
        return col.document(uuid)
            .collection("checked_user").whereEqualTo("status","Ontime").get().await().toObjects(CheckinModel::class.java)
    }

    suspend fun getLate(uuid: String): MutableList<CheckinModel> {

//        val docid: String = db.collection("checkin").document().id
//        Log.d("docid",docid)
        return col.document(uuid)
            .collection("checked_user").whereEqualTo("status","Late").get().await().toObjects(CheckinModel::class.java)
    }

}