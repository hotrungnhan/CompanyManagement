package com.example.companymanagement.model.checkin

import com.google.firebase.firestore.DocumentId
import java.util.*

class CheckinModel {
    var check_date: Date = Date()

    @DocumentId
    var check_id: String = "";
}