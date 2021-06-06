package com.example.companymanagement.viewcontroller.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.companymanagement.R
import com.example.companymanagement.utils.DateParser
import com.example.companymanagement.utils.DateParser.Companion.toHumanReadDate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class UserInfo : Fragment() {
    var isEdit: Boolean = false;
    var auth = FirebaseAuth.getInstance()
    var infomodel: UserInfoViewModel = UserInfoViewModel();
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        infomodel.retriveUserInfo(auth.currentUser?.uid!!)

        var root = inflater.inflate(R.layout.fragment_user_info, container, false)
        // declare id
        var name = root.findViewById<TextView>(R.id.userinfo_name)
        var phone = root.findViewById<TextView>(R.id.userinfo_phonenumber)
        var cemail = root.findViewById<TextView>(R.id.userinfo_email_contact)
        var pos = root.findViewById<TextView>(R.id.userinfo_position)
        var birthday = root.findViewById<TextView>(R.id.userinfo_birthdate)
        var gender = root.findViewById<TextView>(R.id.userinfo_gender)
        var cardid = root.findViewById<TextView>(R.id.userinfo_cardid)
        var cardidprovidedate = root.findViewById<TextView>(R.id.userinfo_card_id_create_date)
        var cardidprovideplace = root.findViewById<TextView>(R.id.userinfo_card_id_create_place)

        //two way binding
        fun applyEdit(isEdit: Boolean) {
            name.isEnabled = isEdit
            cemail.isEnabled = isEdit
            phone.isEnabled = isEdit
            pos.isEnabled = isEdit
            birthday.isEnabled = isEdit
            gender.isEnabled = isEdit;
            cardid.isEnabled = isEdit;
            cardidprovidedate.isEnabled = isEdit;
            cardidprovideplace.isEnabled = isEdit;
        }
        applyEdit(false)
        infomodel.info.observe(viewLifecycleOwner) {
            name.text = it.Name
            cemail.text = it.Email
            phone.text = it.PhoneNumber
            pos.text = it.Position
            birthday.text = it.BirthDate?.toHumanReadDate()
            gender.text = if (it.Gender == true) "Nam" else "Nu"
            cardid.text = it.IDCardNumber
            cardidprovidedate.text = it.IDCardCreateDate?.toHumanReadDate()
            cardidprovideplace.text = it.IDCardCreateLocation
        }

        root.findViewById<FloatingActionButton>(R.id.infouser_btn_update).setOnClickListener {
            isEdit = !isEdit;
            applyEdit(isEdit)

            if (isEdit == false) {
                try {
                    infomodel.info.value?.Name = name.text.toString()
                    infomodel.info.value?.PhoneNumber = phone.text.toString()
                    infomodel.info.value?.Email = cemail.text.toString()
                    infomodel.info.value?.Position = pos.text.toString()
                    infomodel.info.value?.BirthDate = DateParser.parser(birthday.text.toString())

                    infomodel.info.value?.Gender = gender.text.toString() == "Nam"
                    infomodel.info.value?.IDCardNumber = cardid.text.toString()
                    infomodel.info.value?.IDCardCreateDate =
                        DateParser.parser(cardidprovidedate.text.toString())
                    infomodel.info.value?.IDCardCreateLocation = cardid.text.toString()
                    infomodel.updateUserInfo();
                    toastEditSussced()
                } catch (ex: Exception) {
                    toastEditfaild(ex.message!!)
                }
            }
        }
        return root;

    }
    fun toastEditSussced() {
        Toast.makeText(context, "Update Susceed", Toast.LENGTH_SHORT).show()
    }

    fun toastEditfaild(err: String) {
        Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
    }
}