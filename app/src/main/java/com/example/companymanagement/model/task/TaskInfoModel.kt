package com.example.companymanagement.model.task

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.util.*
@Keep
data class TaskInfoModel (
    @get: PropertyName("Content")
    @set: PropertyName("Content")
    var Content: String? = null,

    @get: PropertyName("Deadline")
    @set: PropertyName("Deadline")
    var Deadline: Date? = null,

    @get: PropertyName("IDReceiver")
    @set: PropertyName("IDReceiver")
    var IDReceiver: List<String>? = null,

    @get: PropertyName("NameReceiver")
    @set: PropertyName("NameReceiver")
    var NameReceiver: List<String>? = null,

    @get: PropertyName("Sender")
    @set: PropertyName("Sender")
    var Sender: String? = null,

    @get: PropertyName("SenderName")
    @set: PropertyName("SenderName")
    var SenderName: String? = null,

    @get: PropertyName("Status")
    @set: PropertyName("Status")
    var Status: String? = null,

    @get: PropertyName("Title")
    @set: PropertyName("Title")
    var Title: String? = null,
    ) {
    @DocumentId
    val uid: String? = null

    //document id shall auto parse from doc by to object function . it should be unsetable

    @ServerTimestamp
    @get: PropertyName("SentDate")
    @set: PropertyName("SentDate")
    var SentDate: Date? = null

    init {
        SentDate = Date();
    }

}