package com.example.companymanagement.model.checkin

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await


class CheckinRepository(var col: CollectionReference) {

    suspend fun getOntime(uuid: String): MutableList<CheckinModel> {
        return col.document(uuid)
            .collection("checked_user").whereEqualTo("status", "Ontime").get().await()
            .toObjects(CheckinModel::class.java)
    }

    suspend fun getLate(uuid: String): MutableList<CheckinModel> {

//        val docid: String = db.collection("checkin").document().id
//        Log.d("docid",docid)
        return col.document(uuid)
            .collection("checked_user").whereEqualTo("status", "Late").get().await()
            .toObjects(CheckinModel::class.java)
    }

}