package com.example.companymanagement.viewcontroller.fragment.mainworkspace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companymanagement.model.tweet.CommentModel
import com.example.companymanagement.model.tweet.CommentRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class CommentViewModel(idtweet: String) : ViewModel() {
    val commentList: MutableLiveData<MutableList<CommentModel>> = MutableLiveData()
    val repo =
        CommentRepository(FirebaseFirestore.getInstance().collection("tweet").document(idtweet)
            .collection("comment"))

    init {
        viewModelScope.launch {
            commentList.postValue(repo.getListComment(10));
        }
    }

    fun addComment(data: CommentModel) {
        viewModelScope.launch {
            val newComment = repo.addComment(data)
            if (newComment != null)
                commentList.value?.add(0, newComment!!)
        }
    }
}