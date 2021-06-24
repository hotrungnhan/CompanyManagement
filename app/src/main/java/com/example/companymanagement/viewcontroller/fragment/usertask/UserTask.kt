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
import com.example.companymanagement.model.task.UserTaskModel
import com.example.companymanagement.viewcontroller.adapter.UserTaskAdapter
import com.example.companymanagement.viewcontroller.fragment.user.UserTaskViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*


class UserTask : Fragment(){

    private lateinit var viewModelMain: UserTaskViewModel
    private lateinit var userTaskAdapter: UserTaskAdapter
    private lateinit var taskList: ArrayList<UserTaskModel>
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var db: FirebaseFirestore
    private val taskLayoutManager = LinearLayoutManager(context)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModelMain = ViewModelProvider(this).get(UserTaskViewModel::class.java)
        return inflater.inflate(R.layout.fragment_user_project, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskRecyclerView = view.findViewById(R.id.user_task_container)
        taskRecyclerView.layoutManager = taskLayoutManager
        taskRecyclerView.setHasFixedSize(true)

        taskList = arrayListOf()

        userTaskAdapter = UserTaskAdapter(taskList)
        taskRecyclerView.adapter = userTaskAdapter

        this.taskLayoutManager.orientation = RecyclerView.VERTICAL

        EventChangeListener()
    }
    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        val id = FirebaseAuth.getInstance().currentUser?.uid!!
        //db.collection("task").whereArrayContains("IDReceiver",id)
        db.collection("task").whereArrayContains("IDReceiver","$id")
            .orderBy("SentDate", Query.Direction.DESCENDING)
            .limit(3)
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null){
                        Log.e("Firestore error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            taskList.add(dc.document.toObject(UserTaskModel::class.java))
                        }
                    }

                    userTaskAdapter.notifyDataSetChanged()
                }

            })
    }
}
