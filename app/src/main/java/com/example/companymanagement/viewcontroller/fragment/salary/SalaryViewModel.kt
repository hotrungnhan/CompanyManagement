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



@RequiresApi(Build.VERSION_CODES.O)
class SalaryViewModel : ViewModel() {
    var salary = MutableLiveData<SalaryModel>()

    var salaryRef = FirebaseFirestore.getInstance().collection("salary")
    var salaryRepo = SalaryRepository(salaryRef)

    var performRef = FirebaseFirestore.getInstance().collection("performance")
    var performRepo = PerformanceRepository(performRef)


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


    fun updateSalaryFromPerformance(uuid: String, year : String, month: String){
        viewModelScope.launch {

            var performance = performRepo.getPerformanceDoc(uuid, VNeseDateConverter.convertStringToYearMonth(year, month))
            var newSalary = SalaryModel(0,0,0,0,0,0,0,0)

            //Note : Add : get use position to get BasicSalary
            //BasicSalary now in default 100000
            if (performance != null) {
                newSalary.BasicSalary = 100000 //BasicSalary now in default 100000
                newSalary.OverTimeBonus = performance.computeOverTimeBonus()
                newSalary.TaskBonus = performance.computeTaskBonus()
                newSalary.CheckinFaultCharge = performance.computeAbsenceACharge() + performance.computeAbsenceNACharge() + performance.computeLateCharge()

                newSalary.compute(year, month)
            }
            salaryRepo.updateDoc(uuid, year, month, newSalary)
        }
    }
    fun updateSalaryFromPerformance(uuid: String){
        viewModelScope.launch {


            var performance = performRepo.getPerformanceDoc(uuid, YearMonth.now())
            var newSalary = SalaryModel(0,0,0,0,0,0,0,0)

            //Note : Add : get use position to get BasicSalary

            if (performance != null) {
                newSalary.BasicSalary = 100000 //BasicSalary now in default 100000
                newSalary.OverTimeBonus = performance.computeOverTimeBonus()
                newSalary.TaskBonus = performance.computeTaskBonus()
                newSalary.CheckinFaultCharge = performance.computeCheckinFaultCharge()

                newSalary.compute(YearMonth.now())
            }
            salaryRepo.updateDoc(uuid, Year.now().toString(), YearMonth.now().month.toString(), newSalary)
        }
    }

    fun updatePerformance(uuid: String, new : PerformanceModel) {
        viewModelScope.launch {
            performRepo.updateDoc(uuid, YearMonth.now(), new);
        }
    }
    fun updatePerformance(uuid: String, month : YearMonth, new : PerformanceModel){
        viewModelScope.launch {
            performRepo.updateDoc(uuid, month, new);
        }
    }
    fun generateNewPerformance(uuid: String, year: String, month: String){
        //ToDo : synd with checkin, task function
    }
    //only use for testing
    fun updateSalary(uuid: String) {
        viewModelScope.launch {
            salaryRepo.updateDoc(uuid, getYear() ,getMonth(),salary.value!!);
        }
    }
    fun updateSalaryByYear(uuid: String, year: String) {
        viewModelScope.launch {
            salaryRepo.updateDoc(uuid, year, getMonth(), salary.value!!);
        }
    }
    fun updateSalaryByMonth(uuid: String, month: String) {
        viewModelScope.launch {
            salaryRepo.updateDoc(uuid, getYear(), month, salary.value!!);
        }
    }
    fun updateSalary(uuid: String, year: String, month: String){
        viewModelScope.launch {
            salaryRepo.updateDoc(uuid, year, month, salary.value!!);
        }
    }



    override fun onCleared() {
        super.onCleared()
    }
}