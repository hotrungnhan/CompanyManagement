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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.UserTaskModel
import com.example.companymanagement.viewcontroller.adapter.UserTaskAdapter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.time.Month
import java.util.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectCalendar = view.findViewById(R.id.project_calendar)
        taskRecyclerView = view.findViewById(R.id.task_recyclerView)
        taskRecyclerView.layoutManager = taskLayoutManager
        taskRecyclerView.setHasFixedSize(true)

        taskList = arrayListOf()

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
                Linearview.visibility = View.VISIBLE
            } else {
                Linearview.visibility = View.GONE
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

            viewModelMainProject.taskList.observe(viewLifecycleOwner) {
                if (it == null || it.size == 0) {
                    Linearview.visibility = View.VISIBLE
                } else {
                    Linearview.visibility = View.GONE
                    userTaskAdapter.setData(it)

    //find out more about this function and fix the bug
    //this function is used to load data and catch data changes
    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("task")
            .orderBy("SentDate", Query.Direction.ASCENDING)
            //.whereArrayContains("Deadline",date)
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null){
                        Log.e("Firestore error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            taskList.add(dc.document.toObject(UserTaskModel::class.java))
                        }
                    }

                    userTaskAdapter.notifyDataSetChanged()
                }

            })
    }
}