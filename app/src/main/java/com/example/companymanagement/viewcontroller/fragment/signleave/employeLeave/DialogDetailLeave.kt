package com.example.companymanagement.viewcontroller.fragment.signleave.employeLeave

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.companymanagement.R
import com.example.companymanagement.model.leave.LeaveInfoModel
import com.example.companymanagement.utils.DateParser.Companion.toHumanReadDate

class DialogDetailLeave(var leaveinfo: LeaveInfoModel) : DialogFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.bottomsheet_detail_leave, LinearLayout(activity), false)

        val dialog = Dialog(view.context)
        dialog.setContentView(view)
        dialog.create()

        val user_name = view.findViewById<TextView>(R.id.bt_username)
        val day_leave= view.findViewById<TextView>(R.id.bt_dayleave)
        val daycreate = view.findViewById<TextView>(R.id.bt_daycreate)
        val daystart = view.findViewById<TextView>(R.id.bt_daystart)
        val reason = view.findViewById<TextView>(R.id.bt_reason)

        user_name.text = leaveinfo.name
        daycreate.text = leaveinfo.CreateTime?.toHumanReadDate()
        reason.text = leaveinfo.reason
        day_leave.text = leaveinfo.day_leave.toString()
        daystart.text =  leaveinfo.time_start
        return dialog
    }






}