package com.example.companymanagement.viewcontroller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.salary.SalaryModel
import com.example.companymanagement.utils.VNeseDateConverter
import com.example.companymanagement.utils.VietnamDong
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel
import java.math.BigDecimal

class ManagerSalaryAdapter : RecyclerView.Adapter<ManagerSalaryAdapter.SalaryViewHolder>() {

    private var salaries : MutableList<SalaryModel> = mutableListOf()
    override fun getItemCount(): Int = salaries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_manager_salary, parent, false)

        return SalaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalaryViewHolder, position: Int) {
        val salary = salaries[position]


        holder.name.text = salary.OwnerName

        holder.month.text = VNeseDateConverter.vnConvertMonth(salary.CreateTime!!)
        holder.year.text = VNeseDateConverter.fromDateToYear(salary.CreateTime!!).toString()

        holder.salary.text = VietnamDong(BigDecimal(salary.TotalSalary!!)).toString()

        //ToDo bind avatar : holder.avatar
    }

    fun addSalaries(salaries: List<SalaryModel>) {
        this.salaries.apply {
            clear()
            addAll(salaries)
        }
        notifyDataSetChanged()
    }

    inner class SalaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.mn_salary_user_name)
        val year: TextView = itemView.findViewById(R.id.mn_salary_year)
        val month: TextView = itemView.findViewById(R.id.mn_salary_month)
        val salary: TextView = itemView.findViewById(R.id.mn_salary_amount)

    }

}