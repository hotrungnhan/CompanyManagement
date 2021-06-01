package com.example.companymanagement.viewcontroller.fragment.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.companymanagement.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class Navigation : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navcontroller = this.requireActivity()!!.findNavController(R.id.activity_container)
        val bottomnav = view?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomnav?.setupWithNavController(navcontroller)
    }
}