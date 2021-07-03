package com.example.companymanagement.viewcontroller.adapter
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.task.UserTaskModel
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel

class UserTaskAdapter
    : RecyclerView.Adapter<UserTaskAdapter.TaskHolder>() {

    var list: MutableList<UserTaskModel>? = null
    private lateinit var infomodel: UserInfoViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTaskAdapter.TaskHolder {
        val itemView = LayoutInflater.from(parent.context).
        inflate(R.layout.item_task,parent,false)

        return TaskHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserTaskAdapter.TaskHolder, position: Int) {

        holder.Content.text = list?.get(position)!!.Content
        holder.Deadline.text = list?.get(position)!!.Deadline.toString()
        holder.Title.text = list?.get(position)!!.Title
        holder.SenderName.text = list?.get(position)!!.SenderName
        holder.SentDate.text = list?.get(position)!!.SentDate.toString()
        holder.Status.text = list?.get(position)!!.Status
        val handler = Handler()
        handler.post(Runnable { this.notifyItemChanged(position) })
    }

    override fun getItemCount(): Int = list?.size ?: 0;

    class TaskHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val Content: TextView = itemView.findViewById(R.id.task_content)
        val Deadline: TextView = itemView.findViewById(R.id.task_deadline)
        val SenderName: TextView = itemView.findViewById(R.id.task_sender)
        val SentDate: TextView = itemView.findViewById(R.id.task_sentDate)
        val Status: TextView = itemView.findViewById(R.id.task_status)
        val Title: TextView = itemView.findViewById(R.id.task_title)
    }

    fun setData(data: MutableList<UserTaskModel>) {
        this.list = data;
        this.notifyDataSetChanged()
    }

}