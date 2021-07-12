package com.example.companymanagement.viewcontroller.fragment.task

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.employeemanage.EmployeeRepository
import com.example.companymanagement.model.info.UserInfoModel
import com.example.companymanagement.model.task.TaskInfoRepository
import com.example.companymanagement.model.task.UserTaskModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class TaskViewModel: ViewModel() {
    var TaskList: MutableLiveData<MutableList<UserTaskModel>> = MutableLiveData()
    var repo = TaskInfoRepository(FirebaseFirestore.getInstance().collection("task"))
    var repo_employee = EmployeeRepository(FirebaseFirestore.getInstance().collection("userinfo"))
    var CheckList: MutableLiveData<UserInfoModel> = MutableLiveData()
    init {
        viewModelScope.launch {
            TaskList.value = repo.getTask()
        }
    }
    fun search(string: String){
        viewModelScope.launch {
            TaskList.postValue(repo.getsearch(string))
        }
    }
    fun addTask(task: UserTaskModel) {
        viewModelScope.launch {
            val newdata = repo.addTask(task)
            if (newdata != null) {
                TaskList.value?.add(0, newdata)
                TaskList.postValue(TaskList.value)
            }
        }
    }
    fun updateTask(task: UserTaskModel){
        viewModelScope.launch {
            repo.updateTask(task);
        }
    }
    fun deleteTask(task: UserTaskModel){
        viewModelScope.launch {
            repo.deleteTask(task);
            TaskList.value?.remove(task)
            TaskList.postValue(TaskList.value)
        }
    }
    fun checkTask(str : String) {
        viewModelScope.launch{
            val result = repo_employee.checkEmail(str)
            Log.d("aaa", result.toString())
            if(result != null)
                CheckList.postValue(result)

        }
    }
    fun count(str :String, month: String, year: String) : MutableLiveData<Int> {
        var result : MutableLiveData<Int> = MutableLiveData()
        viewModelScope.launch{
            val cnt = repo.countDone(str,month,year)
            Log.d("vbvb",cnt.toString())
            if (cnt != null) {
                result.value = cnt.size
            }
        }
        return result
    }

}