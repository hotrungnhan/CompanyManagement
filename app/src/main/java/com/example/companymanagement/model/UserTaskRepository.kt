package com.example.companymanagement.model

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.time.Year
import java.util.*

class UserTaskRepository(var col: CollectionReference) {

    val id = FirebaseAuth.getInstance().currentUser?.uid!!
    //call this function in the usertask repository.kt to get data
    suspend fun getTask(
        uuid: String,
        selectedDate:Date,
        year: Int, month: Int, dayOfMonth: Int
    ): MutableList<UserTaskModel>? {

        val startDate = Calendar.getInstance()
        startDate.set(year, month, dayOfMonth-1, 0, 0, 0)
        val startDateTime = startDate.time

        val endDate = Calendar.getInstance()
        endDate.set(year, month, dayOfMonth, 23,59, 59)
        val endDateTime = endDate.time

        Log.d("start Date", startDateTime.toString())
        Log.d("end Date", endDateTime.toString())

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