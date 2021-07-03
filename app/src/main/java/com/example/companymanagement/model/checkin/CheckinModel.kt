package com.example.companymanagement.model.checkin

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class CheckinModel (
    @get: PropertyName("checked_date")
    @set: PropertyName("checked_date")
    var checked_date: Date? = null,

    @get: PropertyName("status")
    @set: PropertyName("status")
    var status: String? = null
){
    @DocumentId
    var check_id: String = ""
    //document id shall auto parse from doc by to object function . it should be unsetable

    @ServerTimestamp
    @get: PropertyName("create_time")
    @set: PropertyName("create_time")
    var CreateTime: Date? = null

    @ServerTimestamp
    @get: PropertyName("update_time")
    @set: PropertyName("update_time")
    var UpdateTime: Date? = null

    init {
        CreateTime = Date()
        UpdateTime = Date()
    }
}