package com.example.companymanagement.viewcontroller.fragment.mainproject

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.companymanagement.R

class MainProject : Fragment() {

    companion object {
        fun newInstance() = MainProject()
    }

    private lateinit var viewModelMain: MainProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModelMain = ViewModelProvider(this).get(MainProjectViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main_project, container, false)
    }
}