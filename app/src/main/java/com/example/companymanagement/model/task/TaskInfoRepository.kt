package com.example.companymanagement.model.task

import android.util.Log
import com.example.companymanagement.model.UserInfoModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.*

class TaskInfoRepository(var col: CollectionReference) {
    suspend fun addTask(task: TaskInfoModel): TaskInfoModel?{
        return col.add(task).await().get().await().toObject(TaskInfoModel::class.java)
    }
    suspend fun getNewTask(uid : String): TaskInfoModel?{
        return col.document(uid).get().await().toObject(TaskInfoModel::class.java)
    }
    suspend fun getTask(): MutableList<TaskInfoModel>?  {
        return col.orderBy("SentDate", Query.Direction.DESCENDING).get()
            .await().toObjects(TaskInfoModel::class.java)
    }
    suspend fun updateTask(task : TaskInfoModel) {
        col.document(task.uid!!).set(task).await()
    }
    suspend fun deleteTask(task : TaskInfoModel) {
        col.document(task.uid!!).delete().await()
    }
    suspend fun getsearch(text: String): MutableList<TaskInfoModel>? {
//        return col.whereEqualTo("user_name",text).orderBy("create_time", Query.Direction.DESCENDING).get()
        return col.whereGreaterThanOrEqualTo("Title",text).get()
            .await()
            .toObjects(TaskInfoModel::class.java)
    }
    suspend fun countDone(text : String, month: String, year: String): List<TaskInfoModel?> {
        val Cal = Calendar.getInstance()

        Cal.set(year.toInt(), month.toInt() - 1, 1,0,0,0)
        val start = Cal.time

        Cal.set(year.toInt(), month.toInt(), 1,0,0,0)
        val end = Cal.time
        val ref = col.whereEqualTo("Status",text).whereGreaterThanOrEqualTo("Deadline",start)
            .whereLessThan("Deadline",end)
            .get().await().documents.map {
                it.toObject(TaskInfoModel::class.java)
            }
        Log.d("time1",start.toString())
        Log.d("time2",end.toString())
        return ref
    }
}
