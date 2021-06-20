package com.example.companymanagement.viewcontroller.fragment.employe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.employeemanage.EmployeeModel
import com.example.companymanagement.model.employeemanage.EmployeeRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class EmployeViewModel: ViewModel() {

    var EmployeeList: MutableLiveData<MutableList<EmployeeModel>> = MutableLiveData()
    var repo = EmployeeRepository(FirebaseFirestore.getInstance().collection("userinfo"))

    init {
        viewModelScope.launch {
            EmployeeList.value = repo.getEmployee(10)
        }
    }

    fun addEmployee(employee: EmployeeModel) {
        viewModelScope.launch {
            val newdata = repo.addNewEmployee(employee)
            if (newdata != null)
                EmployeeList.value?.add(0,newdata)
        }
    }
}