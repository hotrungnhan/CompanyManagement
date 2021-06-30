package com.example.companymanagement.viewcontroller.fragment.manager.salary

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import com.example.companymanagement.databinding.DialogFragmentMnSalarySearchFilterBinding
import com.example.companymanagement.viewcontroller.adapter.NameFilterAdapter
import com.example.companymanagement.viewcontroller.adapter.NameListAdapter
import com.example.companymanagement.viewcontroller.fragment.salary.SalaryViewModel
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel


class SearchAndFilterDialog : DialogFragment(), SearchView.OnCloseListener, AdapterView.OnItemClickListener {
    private var _binding: DialogFragmentMnSalarySearchFilterBinding? = null
    private val binding get() = _binding!!
    private var nameList = arrayListOf<String>()

    private lateinit var nameSearch : SearchView
    private lateinit var testName : AutoCompleteTextView
    private lateinit var resultView : TextView
    private lateinit var autoComplete : SearchView.SearchAutoComplete
    private lateinit var nameAdapter : NameListAdapter

    lateinit var salaryViewModel : SalaryViewModel
    lateinit var userInfoViewModel : UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        salaryViewModel = ViewModelProvider(requireActivity()).get(SalaryViewModel::class.java)
        userInfoViewModel = ViewModelProvider(requireActivity()).get(UserInfoViewModel::class.java)

        _binding = DialogFragmentMnSalarySearchFilterBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners(view)
        nameSearch = view.findViewById(R.id.name_enter)
        resultView = view.findViewById(R.id.test_result_view)
        testName = view.findViewById(R.id.testName)

        setSearchAutoComplete()
        nameSearch.setOnCloseListener(this)

        userInfoViewModel.getName().observe(viewLifecycleOwner, Observer {
            nameAdapter = activity?.let { it1 -> NameListAdapter(it1, nameList) }!!

            autoComplete.setAdapter(nameAdapter)
        })

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners(view: View) {
        val doneButton = view.findViewById<Button>(R.id.done_button)
        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        doneButton.setOnClickListener {
            // TODO: Do some task here
            dismiss()
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setSearchAutoComplete(){
        autoComplete = nameSearch.findViewById(androidx.appcompat.R.id.search_src_text)
        autoComplete.setDropDownBackgroundResource(R.color.white)
        autoComplete.threshold = 1
        autoComplete.onItemClickListener = this
    }



    override fun onClose(): Boolean {
        TODO("Not yet implemented")
        resultView.text = ""
        return false
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
        val selected = parent?.getItemAtPosition(position).toString()
        autoComplete.setText(selected)
        userInfoViewModel.findIdByName(selected).observe(viewLifecycleOwner, Observer {
            var text : String
            for(item in it){
                text += item + " "
            }
            resultView.text = text
        })

    }


}