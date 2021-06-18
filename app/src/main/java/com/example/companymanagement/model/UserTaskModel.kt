package com.example.companymanagement.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class UserTaskModel(
    @get: PropertyName("content")
    @set: PropertyName("content")
    var Content: String? = null,

    @get: PropertyName("deadline")
    @set: PropertyName("dealine")
    var Deadline: com.google.firebase.Timestamp? = null,

    @get: PropertyName("sender")
    @set: PropertyName("sender")
    var Sender: String? = null,

    @get: PropertyName("sentDate")
    @set: PropertyName("sentDate")
    var SentDate: com.google.firebase.Timestamp? = null,

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