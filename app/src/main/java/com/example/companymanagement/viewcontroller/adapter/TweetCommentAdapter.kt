package com.example.companymanagement.viewcontroller.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.tweet.CommentModel
import com.google.android.material.imageview.ShapeableImageView

class TweetCommentAdapter : RecyclerView.Adapter<CommentHolder>() {
    var list: MutableList<CommentModel> = mutableListOf()
    fun setData(list: MutableList<CommentModel>) {
        this.list = list;
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        var root = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tweet_comment, parent, false)
        return CommentHolder(root);
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size;
}

class CommentHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {
    val avatar: ShapeableImageView = itemview.findViewById(R.id.comment_item_owner_avatar)
    val name: TextView = itemview.findViewById(R.id.comment_item_content)
    val date: TextView = itemview.findViewById(R.id.comment_item_owner_create_date)
    val content: TextView = itemview.findViewById(R.id.comment_item_content)
    fun bind(data: CommentModel) {
        content.text = data.Content
        date.text = DateUtils.getRelativeTimeSpanString(data.CreateTime?.time!!)
    }
}