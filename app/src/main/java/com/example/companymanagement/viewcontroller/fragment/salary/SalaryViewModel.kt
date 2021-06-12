package com.example.companymanagement.viewcontroller.fragment.salary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.salary.SalaryModel
import com.example.companymanagement.model.salary.SalaryRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.PropertyName
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class SalaryViewModel : ViewModel() {
    var salary = MutableLiveData<SalaryModel>()
    var ref = FirebaseFirestore.getInstance().collection("salary")
    var repo = SalaryRepository(ref)


    fun addDummy(dummy : SalaryModel){
        salary.value = dummy
    }
    private fun getMonth() = YearMonth.now().month.toString()
    private fun getYear() = YearMonth.now().year.toString()

    fun retriveSalary(uuid: String, year : String, month: String) {
        viewModelScope.launch {
            salary.postValue(repo.getSalaryDoc(uuid, year, month));
        }
    }
    fun updateSalary(uuid: String) {
        viewModelScope.launch {
            repo.updateDoc(uuid, getYear() ,getMonth(),salary.value!!);
        }
    }
    fun updateSalaryByYear(uuid: String, year: String) {
        viewModelScope.launch {
            repo.updateDoc(uuid, year, getMonth(), salary.value!!);
        }
    }
    fun updateSalaryByMonth(uuid: String, month: String) {
        viewModelScope.launch {
            repo.updateDoc(uuid, getYear(), month, salary.value!!);
        }
    }
    fun updateSalary(uuid: String, year: String, month: String){
        viewModelScope.launch {
            repo.updateDoc(uuid, year, month, salary.value!!);
        }
    }
    override fun onCleared() {
        super.onCleared()
    }
}