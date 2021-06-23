package com.example.companymanagement.model.ranking

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

class RankerModel (
    @get: PropertyName("ranker_name")
    @set: PropertyName("ranker_name")
    var name: String = "",
    @get: PropertyName("ranker_position")
    @set: PropertyName("ranker_position")
    var position: String = "",
    @get: PropertyName("ranker_point")
    @set: PropertyName("ranker_point")
    var point: Long = 0
        ){
    @DocumentId
    var uid: String? = null


}