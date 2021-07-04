package com.example.companymanagement.viewcontroller.fragment.mainproject

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.adapter.UserTaskAdapter
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate

class MainProject : Fragment() {

    private lateinit var viewModelMainProject: MainProjectViewModel

    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModelMainProject = ViewModelProvider(this).get(MainProjectViewModel::class.java)
        Log.d("User id", user?.uid.toString())
        val root = inflater.inflate(R.layout.fragment_main_project, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.task_recyclerView)
        val userTaskAdapter = UserTaskAdapter()
        val taskLayoutManager = LinearLayoutManager(context)

        val calendarView = view.findViewById<CalendarView>(R.id.task_calendar)
        val notask = view.findViewById<CardView>(R.id.notask_cardview)
        val now = LocalDate.now()

        //Load data at current date
        viewModelMainProject.retrieveUserTask(user?.uid!!,
            now.year,
            now.monthValue - 1,
            now.dayOfMonth)

        Log.d("currentDay",
            now.year.toString() + " " + now.monthValue.toString() + " " + now.dayOfMonth.toString())

        viewModelMainProject.taskList.value?.clear()
        viewModelMainProject.taskList.observe(viewLifecycleOwner) {
            if (it == null || it.size == 0) {
                notask.visibility = View.VISIBLE
            } else {
                notask.visibility = View.GONE
                userTaskAdapter.setData(it)
            }

        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            //clear old data before loading a new one
            viewModelMainProject.taskList.value?.clear()

            //viewModelMainProject.retrieveUserTask(user?.uid!!, selectedDate)
            viewModelMainProject.retrieveUserTask(user.uid, year, month, dayOfMonth)

            Log.d("selectedDate",
                year.toString() + " " + month.toString() + " " + dayOfMonth.toString())

        }

        taskLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.adapter = userTaskAdapter
        recyclerView.layoutManager = taskLayoutManager
    }

}