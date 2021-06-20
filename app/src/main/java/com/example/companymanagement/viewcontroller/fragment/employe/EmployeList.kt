package com.example.companymanagement.viewcontroller.fragment.employe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.adapter.EmployeeRecyclerViewAdapter
import com.google.firebase.auth.FirebaseAuth


class EmployeList : Fragment() {

    private var EmployeViewModel: EmployeViewModel = EmployeViewModel()
    private var user = FirebaseAuth.getInstance().currentUser;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_employe_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_employeelist)
        val layout = LinearLayoutManager(context)

        val adapter = EmployeeRecyclerViewAdapter()
        EmployeViewModel.EmployeeList.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        layout.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layout
        recyclerView.adapter = adapter

        return view
    }
}

