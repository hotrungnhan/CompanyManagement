package com.example.companymanagement.viewcontroller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.task.UserTaskModel
import com.example.companymanagement.utils.DateParser.Companion.toHumanReadDate

class UserTaskAdapter
    : RecyclerView.Adapter<TaskHolder>() {

    var list: MutableList<UserTaskModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)

        return TaskHolder(itemView)
    }

    fun setData(list: MutableList<UserTaskModel>) {
        this.list = list
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task: UserTaskModel = list!![position]
        holder.Content.text = task.content
        holder.Deadline.text = task.deadline?.toHumanReadDate().toString()
        holder.Title.text = task.title
        holder.SenderName.text = task.senderName
        holder.SentDate.text = task.sentDate?.toHumanReadDate().toString()
        holder.Status.text = task.status
    }

    override fun getItemCount(): Int = list.size

}

class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val Content: TextView = itemView.findViewById(R.id.task_content)
    val Deadline: TextView = itemView.findViewById(R.id.task_deadline)
    val SenderName: TextView = itemView.findViewById(R.id.task_sender)
    val SentDate: TextView = itemView.findViewById(R.id.task_sentDate)
    val Status: TextView = itemView.findViewById(R.id.task_status)
    val Title: TextView = itemView.findViewById(R.id.task_title)
}