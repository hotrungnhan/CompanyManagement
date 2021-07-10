package com.example.companymanagement.viewcontroller.fragment.task

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.model.task.UserTaskModel

class Task_ItemCb(var taskInfo: UserTaskModel, var cb: Boolean) : DialogFragment() {
    private lateinit var taskviewmodel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskviewmodel = ViewModelProvider(this.requireActivity()).get(TaskViewModel::class.java)
        var task = taskInfo.apply {
            if (cb == false)
                Status = "Undone"
            else Status = "Completed"
        }
        taskviewmodel.updateTask(task)
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder = AlertDialog.Builder(context)
//        builder.setTitle("Thông báo")
//        if (cb == true) {
//            builder.setMessage("Xác nhận tình trạng 'Chưa hoàn thành' cho Task này?")
//                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
//
//                })
//                // negative button text and action
//                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
//                    dialog.cancel()
//                })
//        } else {
//            builder.setMessage("Xác nhận tình trạng 'Đã hoàn thành' cho Task này?")
//                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
//
//                })
//                // negative button text and action
//                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
//                    dialog.cancel()
//                })
//        }
//
//        val alert = builder.create()
//        return alert
//    }

}