package com.example.companymanagement.viewcontroller.fragment.user

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.checkin.CheckinModel
import com.example.companymanagement.model.checkin.CheckinRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class CheckinViewModel : ViewModel() {


    val id = FirebaseAuth.getInstance().currentUser?.uid!!
    var listWork: MutableLiveData<MutableList<CheckinModel>> = MutableLiveData();
    var listLate: MutableLiveData<MutableList<CheckinModel>> = MutableLiveData();

    @RequiresApi(Build.VERSION_CODES.O)
    var repo = CheckinRepository(FirebaseFirestore.getInstance().collection("checkin"))

    init{
        viewModelScope.launch {
            listLate.value = repo.getLate(id)
            listWork.value = repo.getOntime(id)
            Log.d("ontime",listWork.value.toString())
        }
    }
}
