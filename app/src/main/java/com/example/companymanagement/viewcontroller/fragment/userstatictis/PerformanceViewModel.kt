package com.example.companymanagement.viewcontroller.fragment.user

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.info.UserInfoModel
import com.example.companymanagement.model.info.UserInfoRepository
import com.example.companymanagement.model.performance.PerformanceModel
import com.example.companymanagement.model.performance.PerformanceRepository
import com.example.companymanagement.model.task.UserTaskRepository
import com.example.companymanagement.model.task.UserTaskModel
import com.example.companymanagement.model.tweet.TweetModel
import com.example.companymanagement.model.tweet.TweetRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class PerformanceViewModel : ViewModel() {

    var per: MutableLiveData<PerformanceModel> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.O)
    var repo = PerformanceRepository(FirebaseFirestore.getInstance().collection("performance"))

    @RequiresApi(Build.VERSION_CODES.O)

    fun retrivePerformance(uuid: String, month: String, year: String) {
        viewModelScope.launch {
            per.postValue(repo.getDocByMonth(uuid,month,year));
            Log.d("Data", per.value.toString())
        }
    }
}
