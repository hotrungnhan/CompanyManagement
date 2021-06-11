package com.example.companymanagement.viewcontroller.fragment.mainworkspace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.tweet.TweetModel
import com.example.companymanagement.viewcontroller.adapter.TweetRecyclerViewAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class MainWorkspace : Fragment() {

    private var TweetViewModel: TweetViewModel = TweetViewModel();
    private var user = FirebaseAuth.getInstance().currentUser;
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

        val adapter = TweetRecyclerViewAdapter()
        TweetViewModel.TweetList.observe(viewLifecycleOwner) {
            adapter.setData(it);
        }
        val postbtn = view.findViewById<Button>(R.id.tweet_post_btn)
        val content = view.findViewById<TextInputEditText>(R.id.tweet_content)
        postbtn.setOnClickListener {
            try {
                if (content.text.toString().isNotBlank())
                    TweetViewModel.addTweet(TweetModel(content.text.toString(), user?.uid!!))
                content.text?.clear()
                adapter.notifyItemRangeInserted(0, 1)
            } catch (err: Exception) {

            }
        }
        layout.orientation = RecyclerView.VERTICAL;
        recyclerView.layoutManager = layout;
        recyclerView.adapter = adapter;

    }
}
