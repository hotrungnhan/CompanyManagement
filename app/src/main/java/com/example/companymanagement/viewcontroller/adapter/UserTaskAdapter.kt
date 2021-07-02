package com.example.companymanagement.viewcontroller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.task.UserTaskModel

class UserTaskAdapter
    : RecyclerView.Adapter<TaskHoler>() {

    var list: MutableList<UserTaskModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHoler {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)

        return TaskHoler(itemView)
    }

    override fun onBindViewHolder(holder: TaskHoler, position: Int) {

        holder.Content.text = list[position].Content
        holder.Deadline.text = list[position].Deadline.toString()
        holder.Title.text = list[position].Title
        holder.Sender.text = list[position].Sender
        holder.SentDate.text = list[position].SentDate.toString()
        holder.Status.text = list[position].Status
    }

    override fun getItemCount(): Int = list.size


    fun setData(data: MutableList<UserTaskModel>) {
        this.list = data;
        this.notifyDataSetChanged()
    }

}

class TaskHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val Content: TextView = itemView.findViewById(R.id.task_content)
    val Deadline: TextView = itemView.findViewById(R.id.task_deadline)
    val Sender: TextView = itemView.findViewById(R.id.task_sender)
    val SentDate: TextView = itemView.findViewById(R.id.task_sentDate)
    val Status: TextView = itemView.findViewById(R.id.task_status)
    val Title: TextView = itemView.findViewById(R.id.task_title)
}