package com.example.companymanagement.viewcontroller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.employeemanage.EmployeeModel
import com.google.android.material.imageview.ShapeableImageView

class EmployeeRecyclerViewAdapter() : RecyclerView.Adapter<EmployeeRecyclerViewAdapter.EmployeeHolder>() {
    var list: MutableList<EmployeeModel>? = null
    class EmployeeHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val fName = itemView.findViewById<TextView>(R.id.employe_fullname)
        val contactmail = itemView.findViewById<TextView>(R.id.employe_mail)
        val avatar = itemView.findViewById<ShapeableImageView>(R.id.employe_avatar)
        fun bind(employee: EmployeeModel){
            fName.text = employee.UserfName
            contactmail.text = employee.UserEmail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_employe,parent,false)
        return EmployeeHolder(view)
    }
    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        holder.bind(list!![position])
    }
    override fun getItemCount(): Int = list?.size ?: 0;
    fun setData(data: MutableList<EmployeeModel>) {
        this.list = data;
        this.notifyDataSetChanged()
    }
}
