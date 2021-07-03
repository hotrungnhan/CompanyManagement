package com.example.companymanagement.viewcontroller.fragment.mainproject

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.adapter.UserTaskAdapter
import com.example.companymanagement.viewcontroller.fragment.user.UserTaskViewModel


class MainProject : Fragment() {

    private var taskModel: UserTaskViewModel = UserTaskViewModel()
    private var userTaskAdapter: UserTaskAdapter = UserTaskAdapter()
    private val taskLayoutManager = LinearLayoutManager(context)
    private lateinit var projectCalendar: CalendarView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        taskModel = ViewModelProvider(this).get(UserTaskViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        projectCalendar = view.findViewById(R.id.project_calendar)
//        val taskRecyclerView: RecyclerView = view.findViewById(R.id.task_recyclerView)
//        taskRecyclerView.layoutManager = taskLayoutManager
//        //taskRecyclerView.setHasFixedSize(true)
//
//        taskRecyclerView.adapter = userTaskAdapter
//
//        this.taskLayoutManager.orientation = RecyclerView.VERTICAL
//
//        projectCalendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
//            val message = "Selected date is: " + dayOfMonth +"/" + (month+1) +"/" + year
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
////        }
//
//        taskModel.TaskList.observe(viewLifecycleOwner) {
//            userTaskAdapter.setData(it)
//        }
    }
}