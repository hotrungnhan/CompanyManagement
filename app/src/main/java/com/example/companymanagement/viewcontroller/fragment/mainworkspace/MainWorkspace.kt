package com.example.companymanagement.viewcontroller.fragment.mainworkspace

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.adapter.TweetRecyclerViewAdapter

class MainWorkspace : Fragment() {

    companion object {
        fun newInstance() = MainWorkspace()
    }

    private lateinit var viewModelMain: MainWorkspaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_main_workspace, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.workspace_tweet_container)
        val layout = LinearLayoutManager(context);
        val adapter = TweetRecyclerViewAdapter(listOf("Test"))
        layout.orientation = RecyclerView.VERTICAL;
        recyclerView.layoutManager = layout;
        recyclerView.adapter = adapter;

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelMain = ViewModelProvider(this).get(MainWorkspaceViewModel::class.java)
    }

}