package com.example.companymanagement.viewcontroller.fragment.actionbar

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.fragment.user.UserManagerBottomSheet
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class ActionBar : Fragment() {
    var auth = FirebaseAuth.getInstance();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_action_bar, container, false)
        var avatar = root.findViewById<ShapeableImageView>(R.id.action_bar_avatar)
        var displayname = root.findViewById<TextView>(R.id.action_bar_display_name)
        var email = root.findViewById<TextView>(R.id.action_bar_email_address)
        var userlayout = root.findViewById<ConstraintLayout>(R.id.action_bar_user_layout)
        Picasso.get().load(auth.currentUser?.photoUrl).resize(32, 32).into(avatar);
        displayname.setText(auth.currentUser?.displayName)
        email.setText(auth.currentUser?.email)
        //
        userlayout.setOnClickListener({ e ->
            UserManagerBottomSheet().show(this.childFragmentManager, "userInfo");
        })
        return root;
    }
}