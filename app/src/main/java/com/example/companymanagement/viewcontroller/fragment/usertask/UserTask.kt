package com.example.companymanagement.viewcontroller.fragment.usertask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.adapter.UserTaskAdapter
import com.example.companymanagement.viewcontroller.fragment.user.UserTaskViewModel


class UserTask : Fragment(){

    private var taskModel: UserTaskViewModel = UserTaskViewModel()
    private var userTaskAdapter: UserTaskAdapter = UserTaskAdapter()
    private val taskLayoutManager = LinearLayoutManager(context)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        taskModel = ViewModelProvider(this).get(UserTaskViewModel::class.java)
        return inflater.inflate(R.layout.fragment_user_project, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskRecyclerView: RecyclerView = view.findViewById(R.id.user_task_container)
        taskRecyclerView.layoutManager = taskLayoutManager
        taskRecyclerView.setHasFixedSize(true)

        taskRecyclerView.adapter = userTaskAdapter

        this.taskLayoutManager.orientation = RecyclerView.VERTICAL

        taskModel.TaskList.observe(this.viewLifecycleOwner) {
            userTaskAdapter.setData(it)
        }
    }
}
