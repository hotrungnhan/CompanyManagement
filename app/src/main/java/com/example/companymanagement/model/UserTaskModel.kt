package com.example.companymanagement.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import java.util.*

data class UserTaskModel(
    @get: PropertyName("content")
    @set: PropertyName("content")
    var Content: String? = null,

    @get: PropertyName("deadline")
    @set: PropertyName("deadline")
    var Deadline: Date? = null,

    @get: PropertyName("sender")
    @set: PropertyName("sender")
    var Sender: String? = null,

    @get: PropertyName("sentDate")
    @set: PropertyName("sentDate")
    var SentDate: Date? = null,

    @get: PropertyName("status")
    @set: PropertyName("status")
    var Status: String? = null,

    @get: PropertyName("title")
    @set: PropertyName("title")
    var Title: String? = null,

) {
    @DocumentId
    val tid:String? = null

}