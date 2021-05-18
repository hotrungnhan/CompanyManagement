package com.example.companymanagement.viewcontroller.fragment.mainworkspace

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.companymanagement.R

class MainWorkspace : Fragment() {

    companion object {
        fun newInstance() = MainWorkspace()
    }

    private lateinit var viewModelMain: MainWorkspaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_workspace, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelMain = ViewModelProvider(this).get(MainWorkspaceViewModel::class.java)
        // TODO: Use the ViewModel
    }

}