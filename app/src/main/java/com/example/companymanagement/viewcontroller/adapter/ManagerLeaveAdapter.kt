package com.example.companymanagement.viewcontroller.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.leave.LeaveInfoModel
import com.example.companymanagement.utils.customize.OnBindOwnerLisener
import com.example.companymanagement.viewcontroller.fragment.signleave.employeLeave.DialogDetailLeave
import com.example.companymanagement.viewcontroller.fragment.signleave.managerleave.DialogAcceptLeave

class ManagerLeaveAdapter: RecyclerView.Adapter<LeaveHolder>() {

    var list : MutableList<LeaveInfoModel>? = null
    private var ownerbindinglisener: OnBindOwnerLisener? = null;
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LeaveHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return LeaveHolder(view)
    }

    fun setData(data: MutableList<LeaveInfoModel>) {
        this.list = data;
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list?.size ?: 10

    override fun onBindViewHolder(holder: LeaveHolder, position: Int) {
        if (list?.get(position) != null) {
            holder.bind(list!![position])
            ownerbindinglisener?.onBind(list!![position].Owneruid!!, holder)
            holder.itemView.setOnClickListener {
                val bt = DialogAcceptLeave( list!![position])
                bt.show((holder.itemView.context as FragmentActivity).supportFragmentManager.beginTransaction(),
                    "itemshow")

            }
        }
    }
    fun setOnBindOwner(e: OnBindOwnerLisener) {
        ownerbindinglisener = e;
    }
}
