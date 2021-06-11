package com.example.companymanagement.model.tweet

import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.DocumentReference

class CommentRepository(tweetref: DocumentReference) {
    private val commentcol = tweetref.collection("comment");
    suspend fun addComment(cmt: CommentModel) {
        commentcol.add(cmt).await()
    }

    suspend fun getListComment(cmt: CommentModel) {
        commentcol.add(cmt).await()
    }

    suspend fun deleteComment(commentuid: String) {
        commentcol.document(commentuid).delete().await()
    }
}