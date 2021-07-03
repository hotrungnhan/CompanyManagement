package com.example.companymanagement.model.leave

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.time.LocalDate

import java.util.*

@Keep
class LeaveInfoModel(
    @get: PropertyName("so_ngay_xin_nghi")
    @set: PropertyName("so_ngay_xin_nghi")
    var day_leave: Int? = null,
    @get :PropertyName("time")
    @set:PropertyName("time")
    var time_start: String? = null,
    @get: PropertyName("Reason")
    @set: PropertyName("Reason")
    var reason: String? = null,
    @get: PropertyName("id_owner")
    @set: PropertyName("id_owner")
    var Owneruid: String? = null,
    @get: PropertyName("user_name")
    @set: PropertyName("user_name")
    var name: String? = null,
    @get: PropertyName("ckeck")
    @set: PropertyName("ckeck")
    var check_Result: Boolean? = false


) {
    @DocumentId
    val luid: String? = null

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