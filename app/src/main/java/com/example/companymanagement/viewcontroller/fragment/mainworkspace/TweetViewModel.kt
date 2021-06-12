package com.example.companymanagement.viewcontroller.fragment.mainworkspace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.tweet.TweetModel
import com.example.companymanagement.model.tweet.TweetRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class TweetViewModel : ViewModel() {
    var TweetList: MutableLiveData<MutableList<TweetModel>> = MutableLiveData();
    var repo = TweetRepository(FirebaseFirestore.getInstance().collection("tweet"))

    init {
        viewModelScope.launch {
//            repo.addNewTweet(
//                TweetModel("Sếp sắp đuổi ae gòi", "SblXTUbvIlVumJdypWZuzHNC3iG3", 10)
//            )
            TweetList.value = repo.getTweet(10)
        }
    }

    fun addTweet(tweet: TweetModel) {
        viewModelScope.launch {
//            repo.addNewTweet(
//                TweetModel("Sếp sắp đuổi ae gòi", "SblXTUbvIlVumJdypWZuzHNC3iG3", 10)
//            )
            val newdata = repo.addNewTweet(tweet);
            if (newdata != null)
                TweetList.value?.add(0, newdata)
        }

    }
}