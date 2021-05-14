package com.example.companymanagement.view.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.companymanagement.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }
}