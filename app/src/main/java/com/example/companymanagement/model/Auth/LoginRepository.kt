package com.example.companymanagement.model.Auth

import android.util.Log
import com.example.companymanagement.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository() {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance();

    fun logout() {
        mAuth.signOut();
    }

    suspend fun login(username: String, password: String): Result<FirebaseUser> {
        //TODO:
        // handle login
        //query login auth ,..... stuck with async
        try {
            var result = mAuth.signInWithEmailAndPassword(username, password).await();
            Log.d("login", "sucesss")
            return Result.Success(result!!.user)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}