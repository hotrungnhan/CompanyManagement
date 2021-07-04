package com.example.companymanagement.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.*

class UserTaskRepository(var col: CollectionReference) {

    val id = FirebaseAuth.getInstance().currentUser?.uid!!
    //call this function in the usertask repository.kt to get data
    suspend fun getTask(
        uuid: String,
        year: Int, month: Int, dayOfMonth: Int
    ): MutableList<UserTaskModel>? {

        val startDate = Calendar.getInstance()
        startDate.set(year, month, dayOfMonth-1, 23, 59, 59)
        val startDateTime = startDate.time

        val endDate = Calendar.getInstance()
        endDate.set(year, month, dayOfMonth, 23,59, 59)
        val endDateTime = endDate.time

        val col1:CollectionReference

        //if you want use both order by and where equal to in some files these field index need to be opposite
        // for example: Deadline ascending so the sentdate must be descending
        val snapshot = col
            .whereArrayContains("IDReceiver", uuid)
            .whereGreaterThanOrEqualTo("Deadline", startDateTime)
            .whereLessThanOrEqualTo("Deadline", endDateTime)
            .orderBy("Deadline", Query.Direction.DESCENDING)
            .get().await()

        return if(snapshot.size() != 0){
            snapshot.toObjects(UserTaskModel::class.java)
        } else{ null }

    }
}