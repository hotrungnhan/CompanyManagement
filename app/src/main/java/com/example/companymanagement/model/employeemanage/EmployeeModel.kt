package com.example.companymanagement.model.employeemanage

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.*
import java.util.*

data class EmployeeModel (
    @get: PropertyName("user_name")
    @set: PropertyName("user_name")
    var UserfName: String = "",
    @get: PropertyName("contact_email")
    @set: PropertyName("contact_email")
    var UserEmail: String = "",
){
    @DocumentId
    val postuid: String? = null

    @get:Exclude
    @set:Exclude
    var employee: List<EmployeeModel> ?= null

    @ServerTimestamp
    @get: PropertyName("create_time")
    @set: PropertyName("create_time")
    var CreateTime: Date? = null

    init {
        CreateTime = Date();
    }
}