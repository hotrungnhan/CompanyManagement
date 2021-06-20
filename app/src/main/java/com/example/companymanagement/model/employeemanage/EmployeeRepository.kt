package com.example.companymanagement.model.employeemanage

import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query

class EmployeeRepository(val col: CollectionReference)  {

    suspend fun addNewEmployee(employee: EmployeeModel): EmployeeModel?{
        return col.add(employee).await().get().await().toObject(EmployeeModel::class.java)
    }
    suspend fun getEmployee(count: Long = 10): MutableList<EmployeeModel>? {
        return col.orderBy("create_time", Query.Direction.DESCENDING).limit(count).get()
            .await()
            .toObjects(EmployeeModel::class.java)
    }
    suspend fun getEmployee(count: Long = 10, startafter: EmployeeModel): MutableList<EmployeeModel>? {
        return col.orderBy("create_time", Query.Direction.DESCENDING).limit(count)
            .startAfter(startafter.CreateTime)
            .get().await().toObjects(EmployeeModel::class.java)
    }
}