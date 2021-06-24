package com.example.companymanagement.viewcontroller.fragment.mainproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.task.UserTaskModel
import com.example.companymanagement.model.task.UserTaskRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MainProjectViewModel : ViewModel() {
    //implement a mutable list data
    var tasks:MutableLiveData<UserTaskModel> = MutableLiveData()

    //implement the repository of the task
    var repository = UserTaskRepository(
        FirebaseFirestore.getInstance().collection("task"))

    //update data with the function
    fun retrieveUserTask(uuid: String){
        viewModelScope.launch {
            tasks.postValue(repository.findDoc(uuid))
        }
    }

    fun updateUserTask() {
        //kích hoạt routine
        viewModelScope.launch {
            repository.updateDoc(tasks.value!! as UserTaskModel)
        }
    }
}