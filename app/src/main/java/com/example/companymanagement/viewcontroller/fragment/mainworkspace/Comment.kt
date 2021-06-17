package com.example.companymanagement.viewcontroller.fragment.mainworkspace

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.model.tweet.CommentModel
import com.example.companymanagement.viewcontroller.adapter.TweetCommentAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class Comment : BottomSheetDialogFragment() {

    lateinit var commentViewModel: CommentViewModel;
    lateinit var UserListViewModel: ListUserParticipantViewModel;
    val cuser = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)

        val bundle = this.arguments
        val idtweet = bundle?.getString("tweet_id");
        Log.d("tweet", idtweet.toString())
        commentViewModel = CommentViewModel(idtweet!!);
        UserListViewModel =
            ViewModelProvider(this.requireActivity()).get(ListUserParticipantViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet_commend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recycle view
        val adapter = TweetCommentAdapter();
        val recycleview: RecyclerView = view.findViewById(R.id.tweet_comment_recycleview)
        val layoutManager = LinearLayoutManager(context);
        layoutManager.reverseLayout = true;
        layoutManager.stackFromEnd = true;
        recycleview.adapter = adapter;
        recycleview.layoutManager = layoutManager;
        commentViewModel.commentList.observe(this.viewLifecycleOwner) {
            adapter.setData(it);
        }
        // newcmt
        val cmtbtn: Button = view.findViewById(R.id.comment_post_btn)
        val content: TextInputEditText = view.findViewById(R.id.comment_newcontent)
        cmtbtn.setOnClickListener {
            commentViewModel.addComment(CommentModel(content.text.toString(), cuser?.uid!!))
                .observe(viewLifecycleOwner) {
                    if (it) {
                        content.text?.clear()
                        adapter.notifyItemInserted(0)
                        recycleview.scrollToPosition(0);
                    }
                }
        }
        adapter.setOnBindAvatar() { uuid, avatar ->
            var url: String?;
            if (UserListViewModel.UserList.value?.containsKey(uuid) == true) {
                url = UserListViewModel.UserList.value?.get(uuid)?.AvatarURL
                Picasso.get().load(url).resize(32, 32).into(avatar);
            } else {
                UserListViewModel.appendUser(uuid).observe(viewLifecycleOwner) {
                    url = it?.AvatarURL
                    Picasso.get().load(url).resize(32, 32).into(avatar);
                }
            }
        }
        var loadmore = view.findViewById<TextView>(R.id.tweet_comment_loadmore)
        loadmore.setOnClickListener { view ->
            commentViewModel.loadOldComment()
        }
        commentViewModel.lastestQuerySize.observe(viewLifecycleOwner) {
            adapter.notifyItemRangeInserted(adapter.list.lastIndex, it)
            recycleview.scrollToPosition(adapter.list.lastIndex);
            if (it < 10) {
                loadmore.setTextColor(Color.GRAY)
                loadmore.isClickable = false;
            }
        }

    }

    //settup bottom sheet
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                behaviour.skipCollapsed = true
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}