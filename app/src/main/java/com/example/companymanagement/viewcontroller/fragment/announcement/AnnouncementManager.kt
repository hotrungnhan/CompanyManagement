package com.example.companymanagement.viewcontroller.fragment.announcement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.adapter.NotiViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AnnouncementManager: BottomSheetDialogFragment()  {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tablayout_notiview, container, false)
        val pager = root.findViewById<ViewPager2>(R.id.noti_manager_viewpager)
        pager.adapter = NotiViewPagerAdapter(this);
        return root;
    }
}