package com.example.companymanagement.model.leave


import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.*

class LeaveInfoRepository(val col: CollectionReference) {
    suspend fun addDoc(uuid: String) {
        col.add(uuid).await();
    }
    suspend fun getLeave(count: Long = 100): MutableList<LeaveInfoModel>? {
        return col.orderBy("create_time", Query.Direction.DESCENDING).limit(count).get().await()
            ?.toObjects(LeaveInfoModel::class.java)
    }

    suspend fun getLeaveByOne(id: String): MutableList<LeaveInfoModel>?{

        return col.whereEqualTo("id_owner",id).orderBy("create_time", Query.Direction.DESCENDING).limit(10).get().await()?.toObjects(LeaveInfoModel::class.java)
    }
    suspend fun addDoc(info: LeaveInfoModel): LeaveInfoModel?{
        return col.add(info).await()?.get()?.await()?.toObject(LeaveInfoModel::class.java)
    }

    suspend fun updateDoc(info: LeaveInfoModel) {
        info.UpdateTime = Date();
        col.document(info.luid!!).set(info).await()
    }

    suspend fun deleteDoc(info: LeaveInfoModel) {
        col.document(info.luid!!).delete().await()
    }
}