package com.example.companymanagement.viewcontroller.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.tweet.TweetModel
import com.google.android.material.imageview.ShapeableImageView

class TweetRecyclerViewAdapter() :
    RecyclerView.Adapter<TweetRecyclerViewAdapter.TweetHolder>() {
    var list: MutableList<TweetModel>? = null

    class TweetHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val likebtn: Button = itemView.findViewById(R.id.tweet_item_like_btn)
        val cmtbtn: Button = itemView.findViewById(R.id.tweet_item_comment_btn)
        val content: TextView = itemView.findViewById(R.id.tweet_item_content)
        val avatar: ShapeableImageView = itemView.findViewById(R.id.tweet_item_owner_avatar)
        val date: TextView = itemView.findViewById(R.id.tweet_item_owner_create_date)
        fun bind(tweet: TweetModel) {
            content.text = tweet.Content;
            date.text = DateUtils.getRelativeTimeSpanString(tweet.CreateTime?.time!!,);
            likebtn.text = tweet.LikeCount.toString();
        }
    };
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TweetHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tweet, parent, false)
        return TweetHolder(view)
    }

    fun setData(data: MutableList<TweetModel>) {
        this.list = data;
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list?.size ?: 0;
    override fun onBindViewHolder(holder: TweetHolder, position: Int) {
        holder.bind(list!![position])
    }
}