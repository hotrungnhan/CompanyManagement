package com.example.companymanagement.viewcontroller.fragment.mainproject

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.UserTaskModel
import com.example.companymanagement.viewcontroller.adapter.UserTaskAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.task_recyclerView)
        val userTaskAdapter: UserTaskAdapter = UserTaskAdapter()
        val taskLayoutManager:LinearLayoutManager = LinearLayoutManager(context)

        val calendarView = view.findViewById<CalendarView>(R.id.task_calendar)
        val Linearview = view.findViewById<LinearLayout>(R.id.task_notification)

        val now = LocalDate.now()
        val currentDate = Date.from(now?.atStartOfDay(ZoneId.systemDefault())?.toInstant())

        //Load data at current date
//        viewModelMainProject.retrieveUserTask(user?.uid!!, currentDate)
//        viewModelMainProject.taskList.value?.clear()
//        viewModelMainProject.taskList.observe(viewLifecycleOwner){
//            if(it == null){
//                Linearview.visibility= View.VISIBLE
//            }
//            else {
//                Linearview.visibility = View.GONE
//                userTaskAdapter.setData(it)
//            }
//        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var selectedDate : java.util.Date = Date((year-1900), month, dayOfMonth)

            //clear old data before loading a new one
            viewModelMainProject.taskList.value?.clear()

            //viewModelMainProject.retrieveUserTask(user?.uid!!, selectedDate)
            viewModelMainProject.retrieveUserTask(user?.uid!!, selectedDate, year, month, dayOfMonth)

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

