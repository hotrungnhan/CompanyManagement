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
import com.example.companymanagement.model.UserTaskModel
import com.example.companymanagement.viewcontroller.adapter.UserTaskAdapter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.time.Month
import java.util.*
import kotlin.collections.ArrayList


class MainProject : Fragment() {

    private lateinit var viewModelMain: MainProjectViewModel
    private lateinit var userTaskAdapter: UserTaskAdapter
    private lateinit var taskList: ArrayList<UserTaskModel>
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var db: FirebaseFirestore
    private val taskLayoutManager = LinearLayoutManager(context)
    private lateinit var projectCalendar: CalendarView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModelMain = ViewModelProvider(this).get(MainProjectViewModel::class.java)
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

        userTaskAdapter = UserTaskAdapter(taskList)
        taskRecyclerView.adapter = userTaskAdapter

        this.taskLayoutManager.orientation = RecyclerView.VERTICAL

        projectCalendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val message = "Selected date is: " + dayOfMonth +"/" + (month+1) +"/" + year
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        //EventChangeListener()
    }

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