package com.example.companymanagement.viewcontroller.fragment.manager.salary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.databinding.FragmentLeaderboardBinding
import com.example.companymanagement.databinding.FragmentManagerSalaryBinding
import com.example.companymanagement.model.ranking.RankerModel
import com.example.companymanagement.viewcontroller.adapter.LeaderBoardAdapter
import com.example.companymanagement.viewcontroller.fragment.leaderboard.RankerViewModel
import com.google.android.material.appbar.MaterialToolbar
import java.time.YearMonth

class SalaryManager : Fragment()  {

    private var _binding: FragmentManagerSalaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentManagerSalaryBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topAppBar = view.findViewById<MaterialToolbar>(R.id.mnSalaryTopAppBar)
        var newDialog = SearchAndFilterDialog()
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {

                    newDialog.show(childFragmentManager, "Filter")
                    // Handle search icon press
                    true
                }
                R.id.detail -> {
                    // Handle more item (inside overflow menu) press
                    true
                }
                R.id.shortcut -> {
                    true
                }
                else -> false
            }
        }
    }
}