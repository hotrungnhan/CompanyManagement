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
import com.example.companymanagement.utils.UtilsFuntion
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel
import com.example.companymanagement.viewcontroller.fragment.user.UserManagerBottomSheet
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class ActionBar : Fragment() {
    val auth = FirebaseAuth.getInstance();
    lateinit var infomodel: UserInfoViewModel;
    var bts = UserManagerBottomSheet.Instance();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        infomodel = ViewModelProvider(this.requireActivity()).get(UserInfoViewModel::class.java)
        infomodel.retriveUserInfo(auth.currentUser?.uid!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_action_bar, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var avatar = view.findViewById<ShapeableImageView>(R.id.action_bar_avatar)
        var displayname = view.findViewById<TextView>(R.id.action_bar_display_name)
        var email = view.findViewById<TextView>(R.id.action_bar_email_address)
        var userlayout = view.findViewById<ConstraintLayout>(R.id.action_bar_user_layout)
        infomodel.info.observe(viewLifecycleOwner) {
            val dp = UtilsFuntion.convertDPToPX(32.0F, resources.displayMetrics).toInt()
            Picasso.get().load(it.AvatarURL).resize(dp, dp).into(avatar);
            displayname.setText(it.Name)
            email.setText(it.Email)
        }
        userlayout.setOnClickListener { e ->
            if (bts.isAdded == false) {
                bts.show(this.childFragmentManager, "userInfo");
            }
        }

    }

}