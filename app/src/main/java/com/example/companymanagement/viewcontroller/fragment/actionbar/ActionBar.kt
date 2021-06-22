package com.example.companymanagement.viewcontroller.fragment.actionbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel
import com.example.companymanagement.viewcontroller.fragment.user.UserManagerBottomSheet
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class ActionBar : Fragment() {
    val auth = FirebaseAuth.getInstance();
    lateinit var infomodel: UserInfoViewModel;
    var bts = UserManagerBottomSheet.Instance();
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        infomodel = ViewModelProvider(this.requireActivity()).get(UserInfoViewModel::class.java)
        infomodel.retriveUserInfo(auth.currentUser?.uid!!)
        // Inflate the layout for this fragment

        //
        var root = inflater.inflate(R.layout.fragment_action_bar, container, false)
        var avatar = root.findViewById<ShapeableImageView>(R.id.action_bar_avatar)
        var displayname = root.findViewById<TextView>(R.id.action_bar_display_name)
        var email = root.findViewById<TextView>(R.id.action_bar_email_address)
        var userlayout = root.findViewById<ConstraintLayout>(R.id.action_bar_user_layout)
        infomodel.info.observe(viewLifecycleOwner) {
            Picasso.get().load(it.AvatarURL).resize(32, 32).into(avatar);
            displayname.setText(it.Name)
            email.setText(it.Email)
        }
        userlayout.setOnClickListener { e ->
            if (bts.isAdded == false) {
                bts.show(this.childFragmentManager, "userInfo");
            }
        }
        return root;
    }
}