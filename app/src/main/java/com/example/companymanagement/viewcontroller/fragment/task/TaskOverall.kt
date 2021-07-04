package com.example.companymanagement.viewcontroller.fragment.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import java.time.Year
import java.util.*

class TaskOverall : Fragment() {
    private lateinit var taskviewmodel: TaskViewModel
    var cal = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskviewmodel = ViewModelProvider(this.requireActivity()).get(TaskViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_task_overall, container, false)
        val done = root.findViewById<TextView>(R.id.taskCompleted)
        val undone = root.findViewById<TextView>(R.id.taskUndone)
        taskviewmodel.count("Undone",(Calendar.getInstance().get(Calendar.MONTH)+1).toString(),Calendar.getInstance().get(Calendar.YEAR).toString()).observe(viewLifecycleOwner){
            Log.d("UnDone",it.toString())
            undone.text = it.toString()
        }

        taskviewmodel.count("Completed",(Calendar.getInstance().get(Calendar.MONTH)+1).toString(),Calendar.getInstance().get(Calendar.YEAR).toString()).observe(viewLifecycleOwner){
            Log.d("Done",it.toString())
            done.text = it.toString()
        }
        return root

    }
}