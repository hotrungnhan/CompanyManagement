package com.example.companymanagement.model

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import java.util.*

class UserTaskRepository(var col: CollectionReference) {
    suspend fun addDoc(uuid: String){
        col.add(uuid).await()
    }

    suspend fun findDoc(uuid: String): UserTaskModel? {
        return col.document(uuid).get().await().toObject(UserTaskModel::class.java)
    }

    suspend fun updateDoc(task: UserTaskModel) {
        col.document(task.tid!!).set(task).await()
    }

    suspend fun deleteDoc(task: UserTaskModel) {
        col.document(task.tid!!).delete().await()
    }
}