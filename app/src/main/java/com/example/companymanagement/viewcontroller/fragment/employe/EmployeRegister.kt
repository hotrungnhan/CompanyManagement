package com.example.companymanagement.viewcontroller.fragment.employe

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.companymanagement.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap


class EmployeRegister : Fragment() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private  val db: FirebaseFirestore = FirebaseFirestore.getInstance()

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
        val edPosition = root.findViewById<EditText>(R.id.ed_rg_position)
        bRegister.setOnClickListener{
            var email = edEmail.text.toString().trim()
            var pass = edPass.text.toString().trim()

            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{ Task ->
                if(Task.isSuccessful()) {
                    Toast.makeText(activity, "Tạo tài khoản thành công", Toast.LENGTH_SHORT)
                        .show()
                    val userID = auth.currentUser!!.uid
                    val documentReference = db.collection("userinfo").document(userID)
                    val user: MutableMap<String, Any> = HashMap()
                    user["birth_date"] = "none"
                    user["contact_email"] = "none"
                    user["create_time"] = Calendar.getInstance().time
                    user["gender"] = "none"
                    user["id_card_number"] = "none"
                    user["id_card_create_date"] = "none"
                    user["id_card_create_location"] = "none"
                    user["phone_number"] = "none"
                    user["position"] = edPosition.text.toString()
                    user["update_time"] = "none"
                    user["user_name"] = edName.text.toString()

                    documentReference.set(user).addOnSuccessListener( OnSuccessListener {
                            fun onSuccess(documentReference: DocumentReference) {
                                Log.d(TAG,
                                    "DocumentSnapshot added with ID: " + documentReference.id)
                            }
                        })
                        .addOnFailureListener(OnFailureListener { e ->
                            Log.w(TAG,
                                "Error adding document",
                                e)
                        })

                }else {
                    Toast.makeText(activity, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
        return root
    }

}