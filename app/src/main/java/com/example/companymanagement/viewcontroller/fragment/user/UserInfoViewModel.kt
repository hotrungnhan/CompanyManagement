package com.example.companymanagement.viewcontroller.fragment.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.UserInfoModel
import com.example.companymanagement.model.UserInfoRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class UserInfoViewModel : ViewModel() {
    var info: MutableLiveData<UserInfoModel> = MutableLiveData();
    var repo = UserInfoRepository(FirebaseFirestore.getInstance().collection("userinfo"))

    fun retriveUserInfo(uuid: String) {
        viewModelScope.launch {
            info.postValue(repo.findDoc(uuid));
        }
    }

    fun updateUserInfo() {
        viewModelScope.launch {
            repo.updateDoc(info.value!!);
        }
    }
}