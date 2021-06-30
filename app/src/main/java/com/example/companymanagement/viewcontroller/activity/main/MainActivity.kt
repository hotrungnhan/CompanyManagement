package com.example.companymanagement.viewcontroller.activity.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.activity.login.LoginActivity
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserRoleViewModel
import com.google.firebase.auth.FirebaseAuth


@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

class MainActivity : AppCompatActivity() {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        infomodel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
        rolemodel = ViewModelProvider(this).get(UserRoleViewModel::class.java)

        if (auth.currentUser == null) {
            goBackLogin()
        }
        auth.addAuthStateListener {
            Log.d("User", it.currentUser.toString());
            if (it.currentUser == null) {
                goBackLogin()
            } else {
                infomodel.retriveUseInfo(it.currentUser!!.uid)
                rolemodel.getRole(it.currentUser!!.uid)
            }
        }
    }
}