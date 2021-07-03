package com.example.companymanagement.viewcontroller.fragment.signleave.managerleave

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.companymanagement.R
import com.example.companymanagement.model.leave.LeaveInfoModel
import com.example.companymanagement.utils.DateParser.Companion.toHumanReadDate
import com.example.companymanagement.viewcontroller.fragment.signleave.employeLeave.ManagerLeaveViewModel

class DialogAcceptLeave(var leaveinfo: LeaveInfoModel) : DialogFragment() {

    private var managerLeavemodel: ManagerLeaveViewModel = ManagerLeaveViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.dialog_accept_leave, LinearLayout(activity), false)

        val dialog = Dialog(view.context)
        dialog.setContentView(view)
        dialog.create()

        val user_name = view.findViewById<TextView>(R.id.bt_username)
        val day_leave = view.findViewById<TextView>(R.id.bt_dayleave)
        val daycreate = view.findViewById<TextView>(R.id.bt_daycreate)
        val daystart = view.findViewById<TextView>(R.id.bt_daystart)
        val reason = view.findViewById<TextView>(R.id.bt_reason)
        val btn_refuse = view.findViewById<Button>(R.id.btn_leave_refuse)
        val btn_accept = view.findViewById<Button>(R.id.btn_leave_accept)

        //set data
        user_name.text = leaveinfo.name
        daycreate.text = leaveinfo.CreateTime?.toHumanReadDate()
        reason.text = leaveinfo.reason
        day_leave.text = leaveinfo.day_leave.toString()
        daystart.text =  leaveinfo.time_start

        //button chap nhan don
        btn_accept.setOnClickListener {
            var leave = leaveinfo.apply {
                check_Result = true
            }
            managerLeavemodel.update(leaveinfo)
            managerLeavemodel.LeaveList.value?.find {
                it.Owneruid == leave.Owneruid
            }.apply {
                this?.check_Result = leave.check_Result
            }
            managerLeavemodel.LeaveList.postValue(
                managerLeavemodel.LeaveList.value
            )
            dialog.dismiss()
        }
        //
        btn_refuse.setOnClickListener {

            var leave = leaveinfo.apply {
                check_Result = false
            }
            managerLeavemodel.update(leaveinfo)
            managerLeavemodel.LeaveList.value?.find {
                it.Owneruid == leave.Owneruid
            }.apply {
                this?.check_Result = leave.check_Result
            }
            managerLeavemodel.LeaveList.postValue(
                managerLeavemodel.LeaveList.value
            )
            dialog.dismiss()
        }



        return dialog
    }
}
