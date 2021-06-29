package com.example.companymanagement.viewcontroller.activity.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.activity.login.LoginActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var mAuth2: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (auth.currentUser == null) {
            goBackLogin()
        }
        auth.addAuthStateListener {
            Log.d("User", it.currentUser.toString());
            if (it.currentUser == null) {
                goBackLogin()
            }
        };
        supportActionBar?.hide();
        setContentView(R.layout.activity_main)
        val firebaseOptions = FirebaseOptions.Builder()
            .setDatabaseUrl(this.resources.getString(R.string.firebase_database_url) )
            .setApiKey(this.resources.getString(R.string.google_api_key))
            .setApplicationId(this.resources.getString(R.string.google_app_id)).build()
        try {
            val myApp =
                FirebaseApp.initializeApp(applicationContext, firebaseOptions, "AnyAppName")
            Log.d("test",myApp.toString())
            mAuth2 = FirebaseAuth.getInstance(myApp)

        } catch (e: IllegalStateException) {
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"))
        }
    }

    fun goBackLogin() {
        val usernull = Intent(this, LoginActivity::class.java)
        startActivity(usernull)
        this.finish()
    }
}