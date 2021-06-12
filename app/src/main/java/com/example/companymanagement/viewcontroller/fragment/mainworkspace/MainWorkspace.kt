package com.example.companymanagement.viewcontroller.fragment.mainworkspace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.tweet.TweetModel
import com.example.companymanagement.viewcontroller.adapter.OnCommentClickListener
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
        adapter.setOnCommentClick(object : OnCommentClickListener {
            override fun onClick(tweetid: String) {
                showComment(tweetid);
            }
        })
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
                adapter.notifyItemInserted(0)
            } catch (err: Exception) {
                toastEditfaild(err.message.toString())
            }
        }

        layout.orientation = RecyclerView.VERTICAL;
        recyclerView.layoutManager = layout;
        recyclerView.adapter = adapter;

    }

    fun showComment(tweetid: String) {
        Log.d("tweet", tweetid.toString())
        val comment = Comment()
        val bundle = Bundle();
        bundle.putString("tweet_id", tweetid)
        comment.arguments = bundle
        comment.show(childFragmentManager, tweetid)
    }

    fun toastEditfaild(err: String) {
        Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
    }
}
