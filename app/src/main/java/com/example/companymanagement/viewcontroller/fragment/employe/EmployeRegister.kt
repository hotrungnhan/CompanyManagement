package com.example.companymanagement.viewcontroller.fragment.employe

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.companymanagement.R
import com.example.companymanagement.model.info.UserInfoModel
import com.example.companymanagement.viewcontroller.adapter.EmployeeRecyclerViewAdapter
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap


class EmployeRegister : Fragment() {

    //    public var mAuth2: FirebaseAuth? = null
    private lateinit var employeViewModel: EmployeViewModel
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var mAuth2: FirebaseAuth? = null
    private  val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private  var selectedpos: String ? = null
    private  var EmployeeList : MutableLiveData<MutableList<UserInfoModel>> = MutableLiveData()
    private var adapter: EmployeeRecyclerViewAdapter = EmployeeRecyclerViewAdapter()
    private lateinit var myApp :FirebaseApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        employeViewModel =  ViewModelProvider(this.requireActivity()).get(EmployeViewModel::class.java)
        val firebaseOptions = FirebaseOptions.Builder()
            .setDatabaseUrl(this.resources.getString(R.string.firebase_database_url) )
            .setApiKey(this.resources.getString(R.string.google_api_key))
            .setApplicationId(this.resources.getString(R.string.google_app_id)).build()
        try {
            myApp = FirebaseApp.initializeApp(this.activity as Context, firebaseOptions, "AnyAppName")
            Log.d("test",myApp.toString())
            mAuth2 = FirebaseAuth.getInstance(myApp)

        } catch (e: IllegalStateException) {
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"))
        }

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
        val spinneradapter = ArrayAdapter(root.context,android.R.layout.simple_spinner_dropdown_item,category)

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
        bRegister.setOnClickListener{
            var email = edEmail.text.toString().trim()
            var pass = edPass.text.toString().trim()

            if (email.isNullOrEmpty() == false && pass.isNullOrEmpty() == false && edName.text != null && selectedpos != null) {
                mAuth2?.createUserWithEmailAndPassword(email,pass)?.addOnCompleteListener{ Task ->
                    if(Task.isSuccessful()) {
                        Toast.makeText(activity, "Tạo tài khoản thành công", Toast.LENGTH_SHORT)
                            .show()
                        val userID = mAuth2?.currentUser!!.uid
                        val documentReference = db.collection("userinfo").document(userID)
                        val user: MutableMap<String, Any> = HashMap()
                        user["avatar_url"] = "none"
                        user["birth_date"] = Calendar.getInstance().time
                        user["contact_email"] = "none"
                        user["create_time"] = Calendar.getInstance().time
                        user["gender"] = "none"
                        user["id_card_number"] = "none"
                        user["idcard_create_date"] = Calendar.getInstance().time
                        user["idcard_create_location"] = "none"
                        user["phone_number"] = "none"
                        user["position"] = selectedpos.toString()
                        user["update_time"] = Calendar.getInstance().time
                        user["user_name"] = edName.text.toString()

                        documentReference.set(user).addOnSuccessListener{
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
                        mAuth2?.signOut()

                    }else {
                        Toast.makeText(activity, "Tạo tài khoản thất bại, vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(root.context,  "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }

        }
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        myApp.delete()
    }
}