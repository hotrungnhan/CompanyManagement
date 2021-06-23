package com.example.companymanagement.viewcontroller.fragment.mainworkspace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.tweet.TweetModel
import com.example.companymanagement.utils.UtilsFuntion
import com.example.companymanagement.utils.customize.EndlessScrollRecyclListener
import com.example.companymanagement.viewcontroller.adapter.TweetRecyclerViewAdapter
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

class MainWorkspace : Fragment() {

    private var tweetviewmodel: TweetViewModel = TweetViewModel();
    private lateinit var userlistppviewmodel: ListUserParticipantViewModel;
    private lateinit var userinfoviewmodel: UserInfoViewModel;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        userlistppviewmodel =
            ViewModelProvider(this.requireActivity()).get(ListUserParticipantViewModel::class.java)
        userinfoviewmodel =
            ViewModelProvider(this.requireActivity()).get(UserInfoViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main_workspace, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.workspace_tweet_container)
        val postbtn = view.findViewById<Button>(R.id.tweet_post_btn)
        val content = view.findViewById<TextInputEditText>(R.id.tweet_content)
        val postavatar = view.findViewById<ShapeableImageView>(R.id.tweet_post_avatar)
        val layout = LinearLayoutManager(context);
        val adapter = TweetRecyclerViewAdapter()
        //
        userinfoviewmodel.info.observe(viewLifecycleOwner) {
            Picasso.get().load(it.AvatarURL).resize(32, 32).into(postavatar);
        }

        adapter.setOnCommentClick {
            showComment(it);
        }
        adapter.setOnLikeClick {
            tweetviewmodel.CountLikeUp(it);
        }
        adapter.setOnBindAvatar { uuid, avatar ->
            var url: String?;
            if (userlistppviewmodel.UserList.value?.containsKey(uuid) == true) {
                url = userlistppviewmodel.UserList.value?.get(uuid)?.AvatarURL
                val dp = UtilsFuntion.convertDPToPX(32.0F, resources.displayMetrics).toInt()
                Picasso.get().load(url).resize(dp, dp).into(avatar);
            } else {
                userlistppviewmodel.appendUser(uuid).observe(viewLifecycleOwner) {
                    url = it?.AvatarURL
                    val dp = UtilsFuntion.convertDPToPX(32.0F, resources.displayMetrics).toInt()
                    Picasso.get().load(url).resize(dp, dp).into(avatar);
                }
            }
        }
        tweetviewmodel.TweetList.observe(viewLifecycleOwner) {
            adapter.setData(it);
        }

        recyclerView.addOnScrollListener(object : EndlessScrollRecyclListener() {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                tweetviewmodel.lazyLoadTweet().observe(viewLifecycleOwner) {
                    adapter.notifyItemRangeInserted(adapter.list?.lastIndex!!, it)
                }
                Log.d("Load nore", "load More")
            }
        })

        postbtn.setOnClickListener {
            try {
                if (content.text.toString().isNotBlank())
                    tweetviewmodel.addTweet(TweetModel(content.text.toString(),
                        userinfoviewmodel.info.value?.uid!!))
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
        Log.d("tweet", tweetid)
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
