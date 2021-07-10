package com.example.companymanagement.model.checkin

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.util.*


class CheckinRepository(var col: CollectionReference) {

    suspend fun getOntime(uuid: String): MutableList<CheckinModel> {
        var db = FirebaseFirestore.getInstance()
        return db.collectionGroup("checked_user")
            .whereEqualTo("owneruuid", uuid)
            .whereEqualTo("status","ontime").get().await().toObjects(CheckinModel::class.java)
    }

    suspend fun getLate(uuid: String): MutableList<CheckinModel> {

//        val docid: String = db.collection("checkin").document().id
//        Log.d("docid",docid)

        var db = FirebaseFirestore.getInstance()
        return db.collectionGroup("checked_user")
            .whereEqualTo("owneruuid", uuid)
            .whereEqualTo("status","late").get().await().toObjects(CheckinModel::class.java)
    }

}