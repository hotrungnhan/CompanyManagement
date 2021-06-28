package com.example.companymanagement.viewcontroller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.ranking.RankerModel
import com.google.android.material.imageview.ShapeableImageView

class LeaderBoardAdapter() : RecyclerView.Adapter<LeaderBoardAdapter.RankerViewHolder>() {

    private var rankers: MutableList<RankerModel> = mutableListOf()

    override fun getItemCount(): Int = rankers.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cardview_leaderboard_employee, parent, false)
        return RankerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankerViewHolder, position: Int) {
        val ranker = rankers[position]

        holder.rank.text = (position + 1).toString()
        holder.name.text = ranker.name
        holder.position.text = ranker.position
        holder.point.text = ranker.point.toString()
        //ToDo bind avatar : holder.avatar
    }

    fun addRankers(rankers: List<RankerModel>) {
        this.rankers.apply {
            clear()
            addAll(rankers)
        }
        notifyDataSetChanged()
    }

    inner class RankerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rank: TextView = itemView.findViewById(R.id.leaderboard_user_rank)
        val name: TextView = itemView.findViewById(R.id.mn_salary_user_name)
        val position: TextView = itemView.findViewById(R.id.mn_salary_uuid)
        val point: TextView = itemView.findViewById(R.id.mn_salary_amount)
        val avatar : ShapeableImageView = itemView.findViewById(R.id.leaderboard_user_avatar)

    }
}