package com.example.companymanagement.viewcontroller.fragment.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.companymanagement.R

class TaskCreate : Fragment(){

    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_task_create, container, false)
//        val spinner = root.findViewById<Spinner>(R.id.spinner_task_create_category)
//        spinner.adapter = ArrayAdapter<CharSequence>(root.context,R.array.receiver_category,)
        return root
    }
}
