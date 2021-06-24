package com.example.companymanagement.viewcontroller.fragment.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.task.UserTaskRepository
import com.example.companymanagement.model.task.UserTaskModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class UserTaskViewModel : ViewModel() {
    var user: MutableLiveData<UserTaskModel> = MutableLiveData();
    var repo = UserTaskRepository(FirebaseFirestore.getInstance().collection("task"))

    fun retriveTaskInfo(uuid: String) {
        viewModelScope.launch {
            user.postValue(repo.findDoc(uuid));
        }
    }
}
