package com.example.companymanagement.viewcontroller.fragment.mainproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R

class MainProject : Fragment() {

    private lateinit var viewModelMain: MainProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModelMain = ViewModelProvider(this).get(MainProjectViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main_project, container, false)
    }
}