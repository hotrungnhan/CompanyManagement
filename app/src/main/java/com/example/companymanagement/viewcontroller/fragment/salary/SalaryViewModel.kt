package com.example.companymanagement.viewcontroller.fragment.salary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.salary.SalaryModel
import com.example.companymanagement.model.salary.SalaryRepository
import com.example.companymanagement.model.performance.PerformanceModel
import com.example.companymanagement.model.performance.PerformanceRepository
import com.example.companymanagement.utils.VNeseDateConverter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.time.Year
import java.time.YearMonth
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class SalaryViewModel : ViewModel() {
    var salary = MutableLiveData<SalaryModel>()

    var salaryRef = FirebaseFirestore.getInstance().collection("salary")
    var salaryRepo = SalaryRepository(salaryRef)



    fun addDummy(dummy : SalaryModel){
        salary.value = dummy
    }
    private fun getMonth() = YearMonth.now().month.toString()
    private fun getYear() = YearMonth.now().year.toString()



    fun retrieveSalary(uuid: String, year : String, month: String) {
        viewModelScope.launch {
            salary.postValue(salaryRepo.getSalaryDoc(uuid, year, month));
        }
    }



    //only use for testing
    fun updateSalary(uuid: String, new : SalaryModel) {
        viewModelScope.launch {
            salaryRepo.updateDoc(uuid, new)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}