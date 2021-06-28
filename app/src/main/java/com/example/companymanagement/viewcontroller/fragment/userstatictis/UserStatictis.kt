package com.example.companymanagement.viewcontroller.fragment.userstatictis

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import com.example.companymanagement.utils.DateParser.Companion.toHumanReadDate
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel
import com.example.companymanagement.viewcontroller.fragment.user.PerformanceViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class UserStatictis : Fragment() {

    val c = Calendar.getInstance().time

    val month = SimpleDateFormat("MM", Locale.getDefault())
    val year = SimpleDateFormat("yyyy", Locale.getDefault())

    val dayofwork = 25
    val daycanabsent = 20

    val user = FirebaseAuth.getInstance().currentUser
    lateinit var performancemodel: PerformanceViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performancemodel = ViewModelProvider(this.requireActivity()).get(PerformanceViewModel::class.java)
        performancemodel.retrivePerformance(user?.uid!!,month.format(c),year.format(c))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_statictis, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = view.findViewById<TextView>(R.id.textview_title)
        val work = view.findViewById<TextView>(R.id.datework)
        val late = view.findViewById<TextView>(R.id.datelate)
        val absent = view.findViewById<TextView>(R.id.dateabsent)
        val canabsent = view.findViewById<TextView>(R.id.datecanabsent)

        title.text = "Thống kê Checkin tháng " + month.format(c).toString() + " / " + year.format(c).toString()

        performancemodel.per.observe(viewLifecycleOwner) {
            work.text = (dayofwork - (it.Late + it.AbsenceA + it.AbsenceNA)).toString() + "/" + dayofwork.toString()
            absent.text = (it.AbsenceA + it.AbsenceNA).toString()
            late.text = it.Late.toString()
            canabsent.text = (daycanabsent - (it.AbsenceA + it.AbsenceNA)).toString()
        }
    }
}


