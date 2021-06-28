package com.example.companymanagement.viewcontroller.fragment.mainhome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.adapter.HomeGridViewApdapter

class MainHome : Fragment() {
    val listview: MutableList<HomeGridViewViewModel> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        createdata()
        return inflater.inflate(R.layout.fragment_main_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.grid_view_container_manager)
        recyclerView.adapter = HomeGridViewApdapter(requireActivity(), listview)
        recyclerView.layoutManager = GridLayoutManager(context, 1);
    }

    fun createdata() {
        listview.add(HomeGridViewViewModel(R.drawable.bg_launcher_background,
            R.id.employee_manager,
            "Employee Manager"))
        listview.add(HomeGridViewViewModel(R.drawable.bg_launcher_background,
            R.id.user_salary,
            "Salary"))
        listview.add(HomeGridViewViewModel(R.drawable.bg_launcher_background,
            R.id.manager_salary,
            "Employees' Salary"))
        listview.add(HomeGridViewViewModel(R.drawable.bg_launcher_background,
            R.id.leaderboard,
            "Leaderboard"))
    }
}