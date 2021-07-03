package com.example.companymanagement.viewcontroller.fragment.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.companymanagement.R
import java.text.DateFormat
import java.util.*

class TaskCreate : Fragment(){

    val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
    val tf = DateFormat.getTimeInstance(DateFormat.SHORT)
    var cal = Calendar.getInstance()
    var textview_date: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_task_create, container, false)

        //spinner select
        val category = resources.getStringArray(R.array.receiver_category)
        val spinner = root.findViewById<Spinner>(R.id.spinner_task_create_category)
        val adapter = ArrayAdapter(root.context,android.R.layout.simple_spinner_dropdown_item,category)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                //Toast.makeText(root.context,  "Đã chọn: " + category[pos], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        //datepick dialog change view
        val dateListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                cal.set(Calendar.YEAR, p1)
                cal.set(Calendar.MONTH, p2)
                cal.set(Calendar.DAY_OF_MONTH, p3)
                updateTextView()
            }
        }

        //deadline datepick dialog
        val datePicker2 = root.findViewById<ImageView>(R.id.img_task_create_calendar2)
        datePicker2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                textview_date = root.findViewById<TextView>(R.id.tv_task_create_deadline)
                DatePickerDialog(root.context,
                    dateListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        // create task datepick dialog
        val senddate = root.findViewById<TextView>(R.id.tv_task_create_senddate)
        senddate.text = df.format(cal.time)
        val datePicker1 = root.findViewById<ImageView>(R.id.img_task_create_calendar1)
        datePicker1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                textview_date = senddate
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
                textview_date!!.text = tf.format(cal.time)
            }
        }
        // deadline timepick dialog
        val timePicker = root.findViewById<ImageView>(R.id.img_task_create_time)
        timePicker.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                textview_date = root.findViewById<TextView>(R.id.tv_task_create_timeline)
                TimePickerDialog(root.context,
                    timeListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),true).show()
            }

        })
        return root
    }
    private fun updateTextView() {
        textview_date!!.text = df.format(cal.time)
    }
}
