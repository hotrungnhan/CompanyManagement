package com.example.companymanagement.viewcontroller.fragment.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.performance.PerformanceModel
import com.example.companymanagement.model.performance.PerformanceRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class PerformanceViewModel : ViewModel() {

    var per: MutableLiveData<PerformanceModel> = MutableLiveData()

    var repo = PerformanceRepository(FirebaseFirestore.getInstance().collection("performance"))


    fun retrivePerformance(uuid: String, month: String, year: String) {
        viewModelScope.launch {
            per.postValue(repo.getDocByMonth(uuid, month, year));
            Log.d("Data", per.value.toString())
        }
    }
}
