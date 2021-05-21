package com.example.companymanagement.viewcontroller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R

class TweetRecyclerViewAdapter(var list: List<String>) :
    RecyclerView.Adapter<TweetRecyclerViewAdapter.TweetHolder>() {

    class TweetHolder(itemView: View) : RecyclerView.ViewHolder(itemView);
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TweetHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tweet, parent, false)
        return TweetHolder(view)
    }


    override fun getItemCount(): Int = list.size;
    override fun onBindViewHolder(holder: TweetHolder, position: Int) {
    }
}