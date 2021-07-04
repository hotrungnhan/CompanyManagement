package com.example.companymanagement.model.task

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.*

class UserTaskRepository(var col: CollectionReference) {
    // apply couroutine
    // it should be handle with try catch if a call is cancelation , for some purpose everyone also know, i throw them.But u guy should implement it

    suspend fun addNewTask(task: UserTaskModel): UserTaskModel? {
        return col.add(task).await().get().await().toObject(UserTaskModel::class.java)
    }

    suspend fun getTask(uuid: String): MutableList<UserTaskModel> {
        return col.whereArrayContains("IDReceiver", "$uuid")
            .get().await()
            .toObjects(UserTaskModel::class.java)
    }

    suspend fun getTask(
        uuid: String,
        year: Int, month: Int, dayOfMonth: Int,
    ): MutableList<UserTaskModel>? {

        val startDate = Calendar.getInstance()
        startDate.set(year, month, dayOfMonth - 1, 23, 59, 59)
        val startDateTime = startDate.time

        val endDate = Calendar.getInstance()
        endDate.set(year, month, dayOfMonth, 23, 59, 59)
        val endDateTime = endDate.time

        val col1: CollectionReference

        //if you want use both order by and where equal to in some files these field index need to be opposite
        // for example: Deadline ascending so the sentdate must be descending
        val snapshot = col
            .whereArrayContains("IDReceiver", uuid)
            .whereGreaterThanOrEqualTo("Deadline", startDateTime)
            .whereLessThanOrEqualTo("Deadline", endDateTime)
            .orderBy("Deadline", Query.Direction.DESCENDING)
            .get().await()

        return if (snapshot.size() != 0) {
            snapshot.toObjects(UserTaskModel::class.java)
        } else {
            null
        }

    }
}