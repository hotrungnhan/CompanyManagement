package com.example.companymanagement.viewcontroller.fragment.manager.salary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import com.example.companymanagement.databinding.DialogFragmentMnSalarySearchFilterBinding
import com.example.companymanagement.viewcontroller.adapter.NameFilterAdapter
import com.example.companymanagement.viewcontroller.fragment.salary.SalaryViewModel


class SearchAndFilterDialog : DialogFragment(), SearchView.OnCloseListener, NameFilterAdapter.OnItemClickListener {
    private var _binding: DialogFragmentMnSalarySearchFilterBinding? = null
    private val binding get() = _binding!!
    private lateinit var nameSearch : SearchView
    private lateinit var resultView : TextView
    private lateinit var autoComplete : SearchView.SearchAutoComplete

    lateinit var salaryViewModel : SalaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        salaryViewModel = ViewModelProvider(requireActivity()).get(SalaryViewModel::class.java)

        _binding = DialogFragmentMnSalarySearchFilterBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners(view)
        nameSearch = view.findViewById<SearchView>(R.id.name_enter)
        resultView = view.findViewById<TextView>(R.id.test_result_view)

        nameSearch.setOnCloseListener(this)

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
        autoComplete.setThreshold(1)
        //autoComplete.setOnItemClickListener(this)
    }



    override fun onClose(): Boolean {
        TODO("Not yet implemented")
        resultView.text = ""
        return false
    }

    override fun onClick(position: Int) {
        TODO("Not yet implemented")
    }


}