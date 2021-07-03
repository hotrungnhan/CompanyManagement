package com.example.companymanagement.utils.customize

import androidx.recyclerview.widget.RecyclerView

fun interface OnButtonClickListener {
    fun onClick(tweetid: String);
}

fun interface OnBindOwnerLisener {
    fun onBind(ownerid: String, vh: RecyclerView.ViewHolder);
}

