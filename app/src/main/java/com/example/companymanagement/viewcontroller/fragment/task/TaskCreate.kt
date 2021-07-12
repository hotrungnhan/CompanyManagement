package com.example.companymanagement.viewcontroller.fragment.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import com.example.companymanagement.model.task.UserTaskModel

import com.example.companymanagement.viewcontroller.adapter.EmployeeRecyclerViewAdapter
import com.example.companymanagement.viewcontroller.adapter.TaskRecyclerViewAdapter
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TaskCreate : Fragment() {
    private lateinit var taskviewmodel: TaskViewModel
    var cal = Calendar.getInstance()
    var textview_date: TextView? = null
    val format: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy' 'HH:mm:ss")
    private var userinfoviewmodel: UserInfoViewModel = UserInfoViewModel()
    private var adapter: TaskRecyclerViewAdapter = TaskRecyclerViewAdapter()
    lateinit var list: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskviewmodel = ViewModelProvider(this.requireActivity()).get(TaskViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_task_create, container, false)
        val taskCreateButton = root.findViewById<Button>(R.id.b_task_creat_done)
        val timePicker = root.findViewById<ImageView>(R.id.img_task_create_time)
        val datePicker2 = root.findViewById<ImageView>(R.id.img_task_create_calendar2)
        val taskContent = root.findViewById<EditText>(R.id.ed_task_create_description)
        val taskSender = root.findViewById<TextView>(R.id.tv_task_create_sender)
        val taskReceiver = root.findViewById<EditText>(R.id.ed_task_create_receiver)
        val dldate = root.findViewById<TextView>(R.id.tv_task_create_deadline)
        val dlhour = root.findViewById<TextView>(R.id.tv_task_create_timeline)
        val taskTitle = root.findViewById<EditText>(R.id.ed_task_create_name)

        //datepick dialog change view
        val dateListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                cal.set(Calendar.YEAR, p1)
                cal.set(Calendar.MONTH, p2)
                cal.set(Calendar.DAY_OF_MONTH, p3)
                textview_date!!.text =
                    p3.toString() + "-" + (p2 + 1).toString() + "-" + p1.toString()
            }
        }

        //deadline datepick dialog
        datePicker2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                textview_date = dldate
                DatePickerDialog(root.context,
                    dateListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })


        // timepick change view
        val timeListener = object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                cal.set(Calendar.HOUR_OF_DAY, p1)
                cal.set(Calendar.MINUTE, p2)
                textview_date!!.text = p1.toString() + ":" + p2.toString() + ":00"
            }
        }
        // deadline timepick dialog
        timePicker.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                textview_date = dlhour
                TimePickerDialog(root.context,
                    timeListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE), true).show()
            }

        })

        taskCreateButton.setOnClickListener {
            if (taskTitle.text.isNullOrEmpty() == false && taskReceiver.text.isNullOrEmpty() == false &&
                dldate.text.isNullOrEmpty() == false && dlhour.text.isNullOrEmpty() == false
            ) {
                val receiver = taskReceiver.text.toString().split(",")
                val sender = userinfoviewmodel.info.value?.uid
                val senderName = userinfoviewmodel.info.value?.Name
                val deadline = dldate.text.toString() + " " + dlhour.text.toString()

                val receiver_checked: MutableList<String> = mutableListOf()
                val name_checked: MutableList<String> = mutableListOf()
                for (tag in receiver) {
                    taskviewmodel.checkTask(tag)
                }

                taskviewmodel.CheckList.observe(viewLifecycleOwner) {
                    Log.d("1111", it.uid.toString())
                    val test = it.uid
                    val temp = it.Name
                    if (test != null && temp != null) {
                        receiver_checked.add(test)
                        name_checked.add(temp)
                    }
                    Log.d("222", receiver_checked.toString())
                    if (receiver_checked.size == receiver.size) {
                        try {
                            if (taskContent.text.isNullOrEmpty() == true)
                                taskContent.setText("")
                            taskviewmodel.addTask(UserTaskModel(taskContent.text.toString(),
                                getDateFromString(deadline),
                                Calendar.getInstance().time,
                                sender,
                                senderName,
                                "Undone",
                                taskTitle.text.toString(),
                                receiver_checked,
                                name_checked))
                            Toast.makeText(context,
                                "Tạo task mới thành công",
                                Toast.LENGTH_SHORT)
                                .show()
                            taskTitle.text.clear()
                            taskContent.text.clear()
                            taskReceiver.text.clear()
                            dldate.text = ""
                            dlhour.text = ""
                        } catch (err: Exception) {
                            Toast.makeText(context,
                                err.message.toString(),
                                Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            } else {
                Toast.makeText(root.context,
                    "Vui lòng nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

    fun getDateFromString(datetoSaved: String?): Date? {
        return try {
            format.parse(datetoSaved)
        } catch (e: ParseException) {
            null
        }
    }

}
