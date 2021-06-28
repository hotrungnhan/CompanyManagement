package com.example.companymanagement.viewcontroller.fragment.salary

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.salary.SalaryModel
import com.example.companymanagement.model.salary.SalaryRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class SalaryViewModel : ViewModel() {
    var salary = MutableLiveData<SalaryModel>()
    var salaryList = MutableLiveData<ArrayList<SalaryModel>>()

    var salaryRef = FirebaseFirestore.getInstance().collection("salary")
    var salaryRepo = SalaryRepository(salaryRef)



    fun addDummy(dummy : SalaryModel){
        salary.value = dummy
    }


    fun showChosenSalary(index : Int){
        salary.postValue(salaryList.value?.get(index))
    }
    fun retrieveSalary(uuid: String, year: Int, month: Int){
        viewModelScope.launch {
            salary.postValue(salaryRepo.getSalaryDoc(uuid, year, month))
        }
    }
    fun retrieveMonthlyDetailSalaryInAYear(uuid: String, year: Int) {
        val list = arrayListOf<SalaryModel>()
        viewModelScope.launch {
            for(month in 1 until 13) {
                salaryRepo.getSalaryDoc(uuid, year, month)
                    ?.let { list.add(it) }
            }
            salaryList.postValue(list)
        }
    }
    fun retieveMonthlySalaryInAYear(uuid: String, year: Int){
        viewModelScope.launch {
            salaryRepo.getListSalary(uuid, 2021)
        }
    }


    //only use for testing
    fun updateSalary(uuid: String, new : SalaryModel) {

        viewModelScope.launch {
            val lastPer = salaryRepo.getLastDoc(uuid)
            Log.e("UID", lastPer?.uid.toString())
            Log.e("Date", lastPer?.CreateTime.toString())

            val now = Date()
            if(lastPer != null && now.before(lastPer.EndTime)){
                salaryRepo.updateDoc(lastPer.uid!!, new)
            }
            else{
                salaryRepo.setDoc(new)
            }
        }
    }

    fun updateSalary(uuid: String, new : SalaryModel, now : Date) {

        viewModelScope.launch {
            val lastPer = salaryRepo.getLastDoc(uuid)
            Log.e("UID", lastPer?.uid.toString())
            Log.e("Date", lastPer?.CreateTime.toString())

            if(lastPer != null && now.before(lastPer.EndTime)){
                salaryRepo.updateDoc(lastPer.uid!!, new)
            }
            else{
                salaryRepo.setDoc(new)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}