package com.example.companymanagement.viewcontroller.fragment.mainproject

import android.os.Build
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
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate


@Suppress("DEPRECATION")
class MainProject : Fragment() {
    private lateinit var viewModelMainProject: MainProjectViewModel
    private lateinit var taskModel:UserTaskViewModel
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        taskModel = ViewModelProvider(this).get(UserTaskViewModel::class.java)
        viewModelMainProject = ViewModelProvider(this).get(MainProjectViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main_project, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.task_recyclerView)
        val userTaskAdapter = UserTaskAdapter()
        val taskLayoutManager = LinearLayoutManager(context)

        val calendarView = view.findViewById<CalendarView>(R.id.task_calendar)
        val Linearview = view.findViewById<LinearLayout>(R.id.task_notification)

        val now = LocalDate.now()

        //Load data at current date
        viewModelMainProject.retrieveUserTask(user?.uid!!, now.year, now.monthValue-1, now.dayOfMonth)

        Log.d("currentDay", now.year.toString() +" "+ now.monthValue.toString() +" "+ now.dayOfMonth.toString())

        viewModelMainProject.taskList.value?.clear()
        viewModelMainProject.taskList.observe(viewLifecycleOwner){
            if(it == null || it.size == 0){
                Linearview.visibility= View.VISIBLE
            }
            else {
                Linearview.visibility = View.GONE
                userTaskAdapter.setData(it)
            }
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            //clear old data before loading a new one
            viewModelMainProject.taskList.value?.clear()

            //viewModelMainProject.retrieveUserTask(user?.uid!!, selectedDate)
            viewModelMainProject.retrieveUserTask(user.uid, year, month, dayOfMonth)

            Log.d("selectedDate", year.toString()+" " + month.toString()+" " + dayOfMonth.toString())

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
            viewModelMainProject.taskList.observe(viewLifecycleOwner){
                if(it == null || it.size==0){
                    Linearview.visibility= View.VISIBLE
                }
                else {
                    Linearview.visibility = View.GONE
                    userTaskAdapter.setData(it)

                }
            }
        }

        taskLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.adapter = userTaskAdapter
        recyclerView.layoutManager = taskLayoutManager
    }

}

