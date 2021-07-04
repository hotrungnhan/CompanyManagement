package com.example.companymanagement.viewcontroller.fragment.employe

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import com.example.companymanagement.model.info.UserInfoModel
import com.example.companymanagement.viewcontroller.adapter.EmployeeRecyclerViewAdapter
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap


class EmployeRegister : Fragment() {

    private lateinit var employeViewModel: EmployeViewModel
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var selectedpos: String? = null
    private var EmployeeList: MutableLiveData<MutableList<UserInfoModel>> = MutableLiveData()
    private var adapter: EmployeeRecyclerViewAdapter = EmployeeRecyclerViewAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        employeViewModel =
            ViewModelProvider(this.requireActivity()).get(EmployeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_employe_register, container, false)
        val bRegister = root.findViewById<Button>(R.id.b_rg_done)
        val edEmail = root.findViewById<EditText>(R.id.ed_rg_mail)
        val edPass = root.findViewById<EditText>(R.id.ed_rg_pass)
        val edName = root.findViewById<EditText>(R.id.ed_rg_account)

        // position spinner
        val category = resources.getStringArray(R.array.position_category)
        val spinnerPosition = root.findViewById<Spinner>(R.id.spinner_employee_regist)
        val spinneradapter =
            ArrayAdapter(root.context, android.R.layout.simple_spinner_dropdown_item, category)

        spinnerPosition.adapter = spinneradapter
        spinnerPosition.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                selectedpos = category[pos]
                //Toast.makeText(root.context,  "Đã chọn: " + category[pos], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        //regist account
        bRegister.setOnClickListener {
            var email = edEmail.text.toString().trim()
            var pass = edPass.text.toString().trim()

            if (email.isNullOrEmpty() == false && pass.isNullOrEmpty() == false && edName.text != null && selectedpos != null) {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { Task ->
                    if (Task.isSuccessful()) {
                        Toast.makeText(activity, "Tạo tài khoản thành công", Toast.LENGTH_SHORT)
                            .show()
                        val userID = auth.currentUser!!.uid
                        val documentReference = db.collection("userinfo").document(userID)
                        val user: MutableMap<String, Any> = HashMap()
                        user["birth_date"] = Calendar.getInstance().time
                        user["contact_email"] = "none"
                        user["create_time"] = Calendar.getInstance().time
                        user["gender"] = "none"
                        user["id_card_number"] = "none"
                        user["id_card_create_date"] = Calendar.getInstance().time
                        user["id_card_create_location"] = "none"
                        user["phone_number"] = "none"
                        user["position"] = selectedpos.toString()
                        user["update_time"] = Calendar.getInstance().time
                        user["user_name"] = edName.text.toString()

                        documentReference.set(user).addOnSuccessListener {
                            employeViewModel.appendEmployee(userID)
                            Log.d(TAG,
                                "DocumentSnapshot added with ID: " + documentReference.id)
                            edEmail.text.clear()
                            edName.text.clear()
                            edPass.text.clear()
                            spinnerPosition.setSelection(0)
                        }
                            .addOnFailureListener(OnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            })


                    } else {
                        Toast.makeText(activity,
                            "Tạo tài khoản thất bại, vui lòng kiểm tra lại thông tin",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(root.context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        return root
    }

}