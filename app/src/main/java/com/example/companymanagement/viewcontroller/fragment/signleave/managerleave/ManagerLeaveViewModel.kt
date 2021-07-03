package com.example.companymanagement.viewcontroller.fragment.signleave.employeLeave

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.leave.LeaveInfoModel
import com.example.companymanagement.model.leave.LeaveInfoRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ManagerLeaveViewModel: ViewModel() {
    var LeaveList : MutableLiveData<MutableList<LeaveInfoModel>> = MutableLiveData()
    var rep = LeaveInfoRepository(FirebaseFirestore.getInstance().collection("leave"))
    init{
        viewModelScope.launch {
            LeaveList.postValue(rep.getLeave(10))
        }
    }
    fun addleave(leave: LeaveInfoModel){
        viewModelScope.launch {
            val newdata1 = rep.addDoc(leave)
            if (newdata1 != null)
            {
                LeaveList.value?.add(0,newdata1)
                LeaveList.postValue(LeaveList.value)
            }
        }
    }
    fun update(data: LeaveInfoModel){
        viewModelScope.launch {
            rep.updateDoc(data)
            LeaveList.postValue(LeaveList.value)
        }
    }
}