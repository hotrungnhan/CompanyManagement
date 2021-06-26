package com.example.companymanagement.model.task

import com.example.companymanagement.model.tweet.TweetModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.*

class UserTaskRepository(var col: CollectionReference) {
    // apply couroutine
    // it should be handle with try catch if a call is cancelation , for some purpose everyone also know, i throw them.But u guy should implement it

    suspend fun addNewTask(task: UserTaskModel): UserTaskModel? {
        return col.add(task).await().get().await().toObject(UserTaskModel::class.java)
    }
    suspend fun getTask(count: Long = 10,uuid: String): MutableList<UserTaskModel> {
        return col.whereArrayContains("IDReceiver","$uuid").orderBy("SentDate", Query.Direction.DESCENDING)
            .limit(count).get().await()
            .toObjects(UserTaskModel::class.java)
    }
}