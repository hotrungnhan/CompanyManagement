package com.example.companymanagement.utils.customize

import com.google.android.material.imageview.ShapeableImageView

fun interface OnButtonClickListener {
    fun onClick(tweetid: String);
}

fun interface OnBindAvatarListener {
    fun onBind(ownerid: String, avatar: ShapeableImageView);
}
