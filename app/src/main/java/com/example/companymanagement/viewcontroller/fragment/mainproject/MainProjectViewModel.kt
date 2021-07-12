package com.example.companymanagement.viewcontroller.fragment.mainproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.task.UserTaskModel
import com.example.companymanagement.model.task.UserTaskRepository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MainProjectViewModel : ViewModel() {
    var taskList: MutableLiveData<MutableList<UserTaskModel>> = MutableLiveData()
    var repository = UserTaskRepository(FirebaseFirestore.getInstance().collection("task"))

    fun retrieveUserTask(
        uuid: String,
        year: Int, month: Int, dayOfMonth: Int,
    ) {
        viewModelScope.launch {
            taskList.postValue(repository.getTask(uuid, year, month, dayOfMonth))
            Log.d("Task List", taskList.value.toString())
        }
    }

    fun updateStatus(task: UserTaskModel, status: String) {
        viewModelScope.launch {
            task.Status = status
            repository.updateTask(task);
        }
    }

}