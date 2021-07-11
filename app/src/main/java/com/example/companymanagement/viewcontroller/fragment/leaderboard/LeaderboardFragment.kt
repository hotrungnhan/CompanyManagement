package com.example.companymanagement.viewcontroller.fragment.leaderboard

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.databinding.FragmentLeaderboardBinding
import com.example.companymanagement.model.ranking.RankerModel
import com.example.companymanagement.viewcontroller.adapter.LeaderBoardAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
class LeaderboardFragment : Fragment() {
    private var _binding: FragmentLeaderboardBinding? = null

    private var leaderboardLayoutManager = LinearLayoutManager(activity)
    private lateinit var leaderboardAdapter: LeaderBoardAdapter

    lateinit var rankerViewModel: RankerViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rankerViewModel = ViewModelProvider(requireActivity()).get(RankerViewModel::class.java)

        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usersList = view.findViewById<RecyclerView>(R.id.rankers_list)


        val champ1 = view.findViewById<ShapeableImageView>(R.id.champ1_avatar)
        val champ2 = view.findViewById<ShapeableImageView>(R.id.champ2_avatar)
        val champ3 = view.findViewById<ShapeableImageView>(R.id.champ3_avatar)


        val monthDisplay = view.findViewById<EditText>(R.id.leaderboard_display_month)
        monthDisplay.setText(YearMonth.now().monthValue.toString())
        var month = monthDisplay.text.toString().toInt()
        val yearDisplay = view.findViewById<EditText>(R.id.leaderboard_display_year)
        yearDisplay.setText(YearMonth.now().year.toString())
        var year = yearDisplay.text.toString().toInt()

        val monthBack = view.findViewById<ImageButton>(R.id.leaderboard_button_month_back)
        val monthNext = view.findViewById<ImageButton>(R.id.leaderboard_button_month_next)

        val yearBack = view.findViewById<ImageButton>(R.id.leaderboard_button_year_back)
        val yearNext = view.findViewById<ImageButton>(R.id.leaderboard_button_year_next)

        leaderboardAdapter = LeaderBoardAdapter()

        monthBack.setOnClickListener{
            if(monthDisplay.text.isNotBlank() && month > 1){
                month -= 1
                monthDisplay.setText(month.toString())
            }
        }
        monthNext.setOnClickListener{
            if(monthDisplay.text.isNotBlank() && month < 12){
                month += 1
                monthDisplay.setText(month.toString())
            }
        }

        yearBack.setOnClickListener{
            if(yearDisplay.text.isNotBlank() && year > 2000){
                year -= 1
                yearDisplay.setText(year.toString())
            }
        }
        yearNext.setOnClickListener{
            if(yearDisplay.text.isNotBlank() && year < YearMonth.now().year){
                year += 1
                yearDisplay.setText(year.toString())
            }
        }


        yearDisplay.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(yearDisplay.text.isNotBlank()) {
                    if(yearDisplay.text.toString().toInt() > YearMonth.now().year || yearDisplay.text.toString().toInt() < 2000)
                    {
                        alertInvalidInput(yearDisplay, "Năm : 2000 - nay", YearMonth.now().year.toString())
                    }
                    else {
                        year = yearDisplay.text.toString().toInt()
                        rankerViewModel.retrieveLeaderBoardIn(yearDisplay.text.toString().toInt(),
                            monthDisplay.text.toString().toInt())
                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        monthDisplay.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(monthDisplay.text.isNotBlank()){
                    if(monthDisplay.text.toString().toInt() < 1 || monthDisplay.text.toString().toInt() > 12)
                    {
                        alertInvalidInput(monthDisplay, "Tháng : 1 - 12", YearMonth.now().monthValue.toString())
                    }
                    else {
                        month = monthDisplay.text.toString().toInt()
                        rankerViewModel.retrieveLeaderBoardIn(yearDisplay.text.toString().toInt(),
                            monthDisplay.text.toString().toInt())
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        rankerViewModel.retrieveLeaderBoardIn(yearDisplay.text.toString().toInt(),
            monthDisplay.text.toString().toInt())

        champ1.setImageDrawable(null)
        champ2.setImageDrawable(null)
        champ3.setImageDrawable(null)

        rankerViewModel.rankList.observe(viewLifecycleOwner, {
            leaderboardAdapter.addRankers(it as List<RankerModel>)

            usersList.apply {
                layoutManager = leaderboardLayoutManager
                adapter = leaderboardAdapter
            }


            if (it.size >= 3) {
                Picasso.get().load(it[0].OwnerAvatar)
                    .resize(100, 100)
                    .into(champ1)
                Picasso.get().load(it[1].OwnerAvatar)
                    .resize(90, 90)
                    .into(champ2)
                Picasso.get().load(it[2].OwnerAvatar)
                    .resize(80, 80)
                    .into(champ3)
            } else {
                if (it.size == 2) {
                    Picasso.get().load(it[0].OwnerAvatar)
                        .resize(100, 100)
                        .into(champ1)
                    Picasso.get().load(it[1].OwnerAvatar)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .resize(90, 90)
                        .into(champ2)
                    champ3.setImageDrawable(null)
                }
                if (it.size == 1) {
                    Picasso.get()
                        .load(it[0].OwnerAvatar)
                        .resize(100, 100)
                        .into(champ1)
                    champ2.setImageDrawable(null)
                    champ3.setImageDrawable(null)
                }
                if (it.size == 0) {
                    champ1.setImageDrawable(null)
                    champ2.setImageDrawable(null)
                    champ3.setImageDrawable(null)
                }
            }


        })


    }
    private fun alertInvalidInput(edit : EditText, message : String, resetInput : String){
        val builder = activity?.let { AlertDialog.Builder(it) }
        if (builder != null) {
            builder.setTitle("Invalid Input")
            builder.setMessage(message)
            builder.setPositiveButton("OK") { dialog, which ->
                edit.setText(resetInput)
            }
            builder.show()
        }

    }

}