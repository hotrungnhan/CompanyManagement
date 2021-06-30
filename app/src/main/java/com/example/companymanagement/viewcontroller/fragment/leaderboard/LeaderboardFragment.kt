package com.example.companymanagement.viewcontroller.fragment.leaderboard

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.databinding.FragmentLeaderboardBinding
import com.example.companymanagement.model.ranking.RankerModel
import com.example.companymanagement.viewcontroller.adapter.LeaderBoardAdapter

@RequiresApi(Build.VERSION_CODES.O)
class LeaderboardFragment : Fragment() {
    private var _binding: FragmentLeaderboardBinding? = null

    private var leaderboardLayoutManager = LinearLayoutManager(activity)
    private lateinit var leaderboardAdapter : LeaderBoardAdapter

    lateinit var rankerViewModel : RankerViewModel

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


        leaderboardAdapter = LeaderBoardAdapter()

        rankerViewModel.retrieveLeaderBoardIn(2021, 6)

        rankerViewModel.rankList.observe(viewLifecycleOwner, {
            leaderboardAdapter.addRankers(it as List<RankerModel>)

            usersList.apply {
                layoutManager = leaderboardLayoutManager
                adapter = leaderboardAdapter
            }

        })

    }

/*    private fun genDum(i : Int) : MutableList<RankerModel>{
        var list = mutableListOf<RankerModel>()
        for(t in 0 until i){
            var temp = RankerModel("AAA", "aaa", "employee", 0)
            list.add(temp)
        }
        return list
    }*/

}