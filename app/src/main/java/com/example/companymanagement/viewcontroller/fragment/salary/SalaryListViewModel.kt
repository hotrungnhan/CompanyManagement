package com.example.companymanagement.viewcontroller.fragment.salary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.salary.SalaryModel
import com.example.companymanagement.model.salary.SalaryRepository
import com.example.companymanagement.utils.BarEntryConverter
import com.example.companymanagement.utils.VNeseDateConverter
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class SalaryListViewModel : ViewModel() {
    var detailSalary = MutableLiveData<ArrayList<SalaryModel>>()
    var ref = FirebaseFirestore.getInstance().collection("salary")
    var repo = SalaryRepository(ref)


    fun retryMonthlyDetailSalaryInAYear(uuid: String, year: String) {
        var list = arrayListOf<SalaryModel>()
        viewModelScope.launch {
            for(month in 1 until 13) {
                repo.getSalaryDoc(uuid, year, VNeseDateConverter.convertMonthFloatToString(month.toFloat()))
                    ?.let { list.add(it) }
            }
            detailSalary.postValue(list)
        }
    }

}