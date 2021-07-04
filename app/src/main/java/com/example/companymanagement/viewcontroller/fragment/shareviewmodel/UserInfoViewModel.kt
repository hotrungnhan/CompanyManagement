package com.example.companymanagement.viewcontroller.fragment.shareviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.info.UserInfoModel
import com.example.companymanagement.model.info.UserInfoRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class UserInfoViewModel : ViewModel() {
    var info: MutableLiveData<UserInfoModel> = MutableLiveData()
    val auth = FirebaseAuth.getInstance().currentUser;
    var repo = UserInfoRepository(FirebaseFirestore.getInstance().collection("userinfo"))

    init {
        viewModelScope.launch {
            info.postValue(repo.findDoc(auth?.uid!!));
        }
        info.observeForever {
            viewModelScope.launch {
                repo.updateDoc(it);
            }
        }
    }
    fun getInfo(uid: String?) {
         viewModelScope.launch {
             if (uid != null) {
                  repo.findDoc(uid)
             }
        }
    }

    fun getInfobyId(uuid: String){
        viewModelScope.launch {
            info.postValue(repo.findDoc(uuid))
        }

    fun getName() : MutableLiveData<ArrayList<String>>{
        var result = MutableLiveData<ArrayList<String>>()
        viewModelScope.launch {
            result.value =  repo.getNameList()
        }
        return result
    }
    fun findNameById(uuid: String) : MutableLiveData<String>{
        var result = MutableLiveData<String>()
        viewModelScope.launch {
            result.value = repo.getNameById(uuid)
        }
        return result
    }
    fun findIdByName(name : String) : MutableLiveData<MutableList<String>>{
        var result = MutableLiveData<MutableList<String>>()
        viewModelScope.launch {
            result.value = repo.getIdByName(name)
        }
        return result
    }
}