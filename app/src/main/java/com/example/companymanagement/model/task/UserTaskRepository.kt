package com.example.companymanagement.model.task

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import java.util.*

class UserTaskRepository(var col: CollectionReference) {
    // apply couroutine
    // it should be handle with try catch if a call is cancelation , for some purpose everyone also know, i throw them.But u guy should implement it
    suspend fun addDoc(uuid: String) {
        col.add(uuid).await();
    }

    suspend fun findDoc(uuid: String): UserTaskModel? {
        return col.document(uuid).get().await().toObject(UserTaskModel::class.java)
    }

    suspend fun updateDoc(user: UserTaskModel) {
        user.UpdateTime = Date();
        col.document(user.taskid!!).set(user).await()
    }

    suspend fun deleteDoc(user: UserTaskModel) {
        col.document(user.taskid!!).delete().await()
    }
}