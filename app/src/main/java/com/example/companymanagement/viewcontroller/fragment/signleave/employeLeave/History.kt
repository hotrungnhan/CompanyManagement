package com.example.companymanagement.viewcontroller.fragment.signleave.employeLeave

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.companymanagement.R
import com.example.companymanagement.model.UserInfoModel
import com.example.companymanagement.utils.UtilsFuntion
import com.example.companymanagement.viewcontroller.adapter.LeaveHolder
import com.example.companymanagement.viewcontroller.adapter.LeaveRecyclerViewAdapter
import com.example.companymanagement.viewcontroller.fragment.mainworkspace.ListUserParticipantViewModel
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel

/**
 * A fragment representing a list of Items.
 */
class History : Fragment() {

    private var leaveviewmodel: LeaveViewModel = LeaveViewModel()
    private lateinit var userlistppviewmodel: ListUserParticipantViewModel;
    private lateinit var userinfoviewmodel: UserInfoViewModel;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_list, container, false)
        userlistppviewmodel =
            ViewModelProvider(this.requireActivity()).get(ListUserParticipantViewModel::class.java)
        userinfoviewmodel =
            ViewModelProvider(this.requireActivity()).get(UserInfoViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout = LinearLayoutManager(context)
        var recyclerView = view.findViewById<RecyclerView>(R.id.listhistory)
        var adapter = LeaveRecyclerViewAdapter()

        layout.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layout
        recyclerView.adapter = adapter
        Log.d("ab","adapter.toString()")
        userinfoviewmodel.info.observe(viewLifecycleOwner){
            if (it != null){
                it?.uid?.let { it1 -> leaveviewmodel.getleave(it1)}
            }
        }
        leaveviewmodel.LeaveList.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        }

        adapter.setOnBindOwner { uuid, vh: RecyclerView.ViewHolder ->
            if (vh is LeaveHolder) {
                fun bind(user: UserInfoModel?, vh: LeaveHolder) {
                    val dp = UtilsFuntion.convertDPToPX(32.0F, resources.displayMetrics).toInt()
                    Glide.with(this).load(user?.AvatarURL)
                        .placeholder(CircularProgressDrawable(requireContext()).apply { start() })
                        .override(dp, dp)
                        .centerCrop()
                        .error(R.drawable.ic_default_avatar)
                        .into(vh.avatar)
                    vh.name.text = user?.Name
                }
                if (userlistppviewmodel.UserList.value?.containsKey(uuid) == true) {
                    bind(userlistppviewmodel.UserList.value?.get(uuid), vh);
                } else {
                    userlistppviewmodel.appendUser(uuid).observe(viewLifecycleOwner) {
                        bind(it, vh);
                    }
                }

            }
        }

    }



}