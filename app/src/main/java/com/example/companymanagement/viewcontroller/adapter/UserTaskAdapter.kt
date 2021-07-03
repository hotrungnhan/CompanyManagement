package com.example.companymanagement.viewcontroller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.UserTaskModel
import com.example.companymanagement.utils.DateParser.Companion.toHumanReadDate

class UserTaskAdapter() : RecyclerView.Adapter<UserTaskAdapter.TaskHoler>() {

    var taskList: MutableList<UserTaskModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTaskAdapter.TaskHoler {
        val itemView = LayoutInflater.from(parent.context).
        inflate(R.layout.item_task,parent,false)

        return TaskHoler(itemView)
    }

    fun setData(data: MutableList<UserTaskModel>) {
        this.taskList = data;

        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserTaskAdapter.TaskHoler, position: Int) {

        val task: UserTaskModel = taskList!![position]
        holder.Content.text = task.Content
        holder.Deadline.text = task.Deadline?.toHumanReadDate().toString()
        holder.Title.text = task.Title
        holder.SenderName.text = task.SenderName
        holder.SentDate.text = task.SentDate?.toHumanReadDate().toString()
        holder.Status.text = task.Status
    }

    override fun getItemCount(): Int = taskList?.size ?: 0;

    class TaskHoler (itemView: View) : RecyclerView.ViewHolder(itemView){
        val Content: TextView = itemView.findViewById(R.id.task_content)
        val Deadline: TextView = itemView.findViewById(R.id.task_deadline)
        val SenderName: TextView = itemView.findViewById(R.id.task_sender)
        val SentDate: TextView = itemView.findViewById(R.id.task_sentDate)
        val Status: TextView = itemView.findViewById(R.id.task_status)
        val Title: TextView = itemView.findViewById(R.id.task_title)
    }
}