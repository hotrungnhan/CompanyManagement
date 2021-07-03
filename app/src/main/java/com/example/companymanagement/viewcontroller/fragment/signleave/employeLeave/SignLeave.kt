package com.example.companymanagement.viewcontroller.fragment.signleave.employeLeave

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import com.example.companymanagement.model.leave.LeaveInfoModel
import com.example.companymanagement.viewcontroller.adapter.LeaveRecyclerViewAdapter
import com.example.companymanagement.viewcontroller.fragment.mainworkspace.ListUserParticipantViewModel
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel
import java.text.SimpleDateFormat
import java.util.*


class SignLeave : Fragment() {
    private var leaveviewmodel: LeaveViewModel = LeaveViewModel()
    private var managerleaveviewmodel: ManagerLeaveViewModel = ManagerLeaveViewModel()
    private lateinit var userlistppviewmodel: ListUserParticipantViewModel;
    private lateinit var userinfoviewmodel: UserInfoViewModel;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userinfoviewmodel =
            ViewModelProvider(this.requireActivity()).get(UserInfoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sign_leave, container, false)
        val editTime = root.findViewById<TextView>(R.id.txt_time)
        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd/MM/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                editTime!!.text = sdf.format(cal.getTime())
            }
        editTime.setOnClickListener {
            context?.let { it1 ->
                DatePickerDialog(it1,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()

            }
        }
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_nop = view.findViewById<Button>(R.id.b_nopdon)
        val day_leave: EditText = view.findViewById(R.id.txt_day_leave)
        val editTime = view.findViewById<TextView>(R.id.txt_time)
        val reason: EditText = view.findViewById(R.id.editLido)
        val adapter = LeaveRecyclerViewAdapter()
        btn_nop.setOnClickListener {
            try {
            leaveviewmodel?.addleave(LeaveInfoModel(day_leave.text.toString().toInt(),editTime.text.toString(),reason.text.toString(),userinfoviewmodel.info.value?.uid!!,userinfoviewmodel.info.value?.Name,false))
            day_leave.text.clear()
            reason.text.clear()

            Toast.makeText(context, "da them thanh cong", Toast.LENGTH_SHORT).show()
        }
            catch (err: Exception) {
                toastEditfaild(err.message.toString())
            }
        }
    }

    fun toastEditfaild(err: String) {
        Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
    }
}

