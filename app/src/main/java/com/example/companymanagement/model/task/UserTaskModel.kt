package com.example.companymanagement.model.task

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

@Keep
data class UserTaskModel(
    @get: PropertyName("content")
    @set: PropertyName("content")
    var content: String? = null,

    @get: PropertyName("deadline")
    @set: PropertyName("deadline")
    var deadline: Date? = null,

    @get: PropertyName("sentDate")
    @set: PropertyName("sentDate")
    var sentDate: Date? = null,

    @get: PropertyName("senderName")
    @set: PropertyName("senderName")
    var senderName: String? = null,

    @get: PropertyName("status")
    @set: PropertyName("status")
    var status: String? = null,

    @get: PropertyName("title")
    @set: PropertyName("title")
    var title: String? = null,

    @get: PropertyName("IDReceiver")
    @set: PropertyName("IDReceiver")
    var IDReceiver: List<String>? = null,

    ) {
    @DocumentId
    val taskid: String? = null

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
        CreateTime = Date();
        UpdateTime = Date();
    }
}