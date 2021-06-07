package com.example.companymanagement.viewcontroller.fragment.signleave

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import com.example.companymanagement.R
import java.security.AccessControlContext
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignLeave.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignLeave : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_sign_leave, container, false)
        val editLoaidon = root.findViewById<TextView>(R.id.txt_loai_don)
        val editTime = root.findViewById<TextView>(R.id.txt_time)

        editLoaidon.setOnClickListener {v: View ->
        val dialoglayout : View = layoutInflater.inflate(R.layout.dialog_reason_leave,null)
            val dialog =
                AlertDialog.Builder(context)
                    .setView(dialoglayout)
                    .setNegativeButton(
                        "Cancel"
                    ){dialog,id ->}
                    .setPositiveButton(
                        "Chon"
                    ){dialog,id ->
                        //code
                    }
                    .setTitle("Loại đơn")
                    .create()
        dialog.show()
        }

        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                //updateDateInView()
            }
        /**editTime.setOnClickListener {
            DatePickerDialog(c,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }*/


        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}

