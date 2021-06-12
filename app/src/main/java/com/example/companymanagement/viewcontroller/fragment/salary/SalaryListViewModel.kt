package com.example.companymanagement.viewcontroller.fragment.salary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.salary.SalaryModel
import com.example.companymanagement.model.salary.SalaryRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class SalaryListViewModel : ViewModel() {
    var salary = MutableLiveData<ArrayList<SalaryModel>>()
    var ref = FirebaseFirestore.getInstance().collection("salary")
    var repo = SalaryRepository(ref)


    private fun getMonth(): String{
        var formatter = DateTimeFormatter.ofPattern("MMMM-yyyy")
        return YearMonth.now().format(formatter)
    }
    fun retriveSalary(uuid: String, year: String) {
        viewModelScope.launch {
            val list = ArrayList<SalaryModel>()
            /*repo.getSalaryDoc()
            salary.postValue());*/
        }
    }
}