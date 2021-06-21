package com.example.companymanagement.viewcontroller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.UserTaskModel

class UserTaskAdapter(private val taskList: ArrayList<UserTaskModel>)
    : RecyclerView.Adapter<UserTaskAdapter.TaskHoler>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTaskAdapter.TaskHoler {
        val itemView = LayoutInflater.from(parent.context).
        inflate(R.layout.item_task,parent,false)

        return TaskHoler(itemView)
    }

    override fun onBindViewHolder(holder: UserTaskAdapter.TaskHoler, position: Int) {

        val task: UserTaskModel = taskList[position]
        holder.Content.text = task.Content
        holder.Deadline.text = task.Deadline
        holder.Title.text = task.Title
        holder.Sender.text = task.Sender
        holder.SentDate.text = task.SentDate?.toDate().toString()
        holder.Status.text = task.Status
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class TaskHoler (itemView: View) : RecyclerView.ViewHolder(itemView){
        val Content: TextView = itemView.findViewById(R.id.task_content)
        val Deadline: TextView = itemView.findViewById(R.id.task_deadline)
        val Sender: TextView = itemView.findViewById(R.id.task_sender)
        val SentDate: TextView = itemView.findViewById(R.id.task_sentDate)
        val Status: TextView = itemView.findViewById(R.id.task_status)
        val Title: TextView = itemView.findViewById(R.id.task_title)
    }
}