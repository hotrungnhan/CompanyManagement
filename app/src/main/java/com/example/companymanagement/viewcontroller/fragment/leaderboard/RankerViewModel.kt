package com.example.companymanagement.viewcontroller.fragment.leaderboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.performance.PerformanceModel
import com.example.companymanagement.model.ranking.RankerModel
import com.example.companymanagement.model.ranking.RankingRepository
import com.example.companymanagement.model.salary.SalaryRepository
import com.example.companymanagement.utils.VNeseDateConverter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.time.YearMonth


@RequiresApi(Build.VERSION_CODES.O)
class RankerViewModel : ViewModel() {
    var rankList = MutableLiveData<List<RankerModel?>>()
    var champlist = MutableLiveData<List<RankerModel?>>()

    var ref = FirebaseFirestore.getInstance().collection("ranking")
    var repo = RankingRepository(ref)

    var salaryRef = FirebaseFirestore.getInstance().collection("salary")
    var salaryRepo = SalaryRepository(salaryRef)

    fun retrieveLeaderBoardIn(month: YearMonth) {
        viewModelScope.launch {
            rankList.postValue(repo.loadLeaderBoardIn(month))
            champlist.postValue(rankList.value?.take(3))

            for(i in 0 until 3){
                rankList.value?.get(i)?.let {
                    it.uid?.let { it1 ->
                        salaryRepo.updateRankBonus(
                            it1,
                            month.year.toString(),
                            VNeseDateConverter.convertMonthFloatToString(month.monthValue.toFloat()),
                            getRankBonusOf(i + 1))
                    }
                }
            }
        }
    }

    fun getRankBonusOf(champ : Int) : Long {
        when(champ){
            1 -> return 1000000
            2 -> return 500000
            3 -> return 200000
            else -> return 0
        }
    }


}