package com.example.companymanagement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.companymanagement.ui.main.Unknown

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, Unknown.newInstance())
                .commitNow()
        }
    }
}