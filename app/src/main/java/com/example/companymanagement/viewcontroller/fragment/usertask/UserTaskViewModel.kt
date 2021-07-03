package com.example.companymanagement.viewcontroller.fragment.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.task.UserTaskRepository
import com.example.companymanagement.model.task.UserTaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class UserTaskViewModel : ViewModel() {

    val id = FirebaseAuth.getInstance().currentUser?.uid!!
    var TaskList: MutableLiveData<MutableList<UserTaskModel>> = MutableLiveData();

    private var repo = UserTaskRepository(FirebaseFirestore.getInstance().collection("task"))

    init {
        viewModelScope.launch {
            TaskList.value = repo.getTask(10,id)
            Log.d("Data", TaskList.value.toString())
        }
    }
}
