package com.example.companymanagement.viewcontroller.fragment.mainproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.UserTaskModel
import com.example.companymanagement.model.UserTaskRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MainProjectViewModel : ViewModel() {
    //implement a mutable list data
    //var taskList: MutableLiveData<MutableList<UserTaskModel>> = MutableLiveData()
    var taskList: MutableLiveData<MutableList<UserTaskModel>> = MutableLiveData()

    //implement the repository of the task
    //var repository = UserTaskRepository(FirebaseFirestore.getInstance().collection("task"))
    var repository = UserTaskRepository(FirebaseFirestore.getInstance().collection("task"))

    //update data with the function
    //call this function in main project.kt to load data
    fun retrieveUserTask(
        uuid: String,
        year: Int, month: Int, dayOfMonth: Int
    ){
        viewModelScope.launch {
            taskList.postValue(repository.getTask(uuid, year, month, dayOfMonth))
            Log.d("Task List", taskList.value.toString())
        }
    }
}