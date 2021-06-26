package com.example.companymanagement.viewcontroller.fragment.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import com.example.companymanagement.utils.DateParser
import com.example.companymanagement.utils.DateParser.Companion.toHumanReadDate
import com.example.companymanagement.utils.UtilsFuntion
import com.example.companymanagement.viewcontroller.fragment.shareviewmodel.UserInfoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.*

class UserInfo : Fragment() {
    var isEdit: Boolean = false;
    val user = FirebaseAuth.getInstance().currentUser
    val cloudstore = FirebaseStorage.getInstance()
    lateinit var infomodel: UserInfoViewModel
    val imagepicked: MutableLiveData<Uri> = MutableLiveData(null);
    lateinit var register: ActivityResultLauncher<Intent>;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        infomodel = ViewModelProvider(this.requireActivity()).get(UserInfoViewModel::class.java)
        // register Image getter Activity

        register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data?.data is Uri)
                imagepicked.postValue(it.data?.data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // declare id
        val name = view.findViewById<TextView>(R.id.userinfo_name)
        val phone = view.findViewById<TextView>(R.id.userinfo_phonenumber)
        val cemail = view.findViewById<TextView>(R.id.userinfo_email_contact)
        val pos = view.findViewById<TextView>(R.id.userinfo_position)
        val birthday = view.findViewById<TextView>(R.id.userinfo_birthdate)
        val gender = view.findViewById<TextView>(R.id.userinfo_gender)
        val cardid = view.findViewById<TextView>(R.id.userinfo_cardid)
        val cardidprovidedate = view.findViewById<TextView>(R.id.userinfo_card_id_create_date)
        val cardidprovideplace = view.findViewById<TextView>(R.id.userinfo_card_id_create_place)
        val uploadavatar = view.findViewById<ShapeableImageView>(R.id.avatar_change)
        val password = view.findViewById<TextInputEditText>(R.id.password_new)
        val passwordretype = view.findViewById<TextInputEditText>(R.id.password_new_retry)

        imagepicked.postValue(Uri.parse(infomodel.info.value?.AvatarURL))
        uploadavatar.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            register.launch(i);
        }
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
        imagepicked.observe(this.viewLifecycleOwner) {
            val dp = UtilsFuntion.convertDPToPX(150.0F, resources.displayMetrics).toInt()
            Picasso.get().load(imagepicked.value).resize(dp, dp).into(uploadavatar);
        }
        infomodel.info.observe(viewLifecycleOwner) {
            name.text = it.Name
            cemail.text = it.Email
            phone.text = it.PhoneNumber
            pos.text = it.Position
            birthday.text = it.BirthDate?.toHumanReadDate()
            gender.text = it.Gender
            cardid.text = it.IDCardNumber
            cardidprovidedate.text = it.IDCardCreateDate?.toHumanReadDate()
            cardidprovideplace.text = it.IDCardCreateLocation
        }
        view.findViewById<Button>(R.id.avatar_accept).setOnClickListener {
            uploadImage()
        }
        view.findViewById<Button>(R.id.password_change_btn).setOnClickListener {

            if (password.text.toString() == passwordretype.text.toString()) {
                updateNewPassword(passwordretype.text.toString())
                password.text?.clear()
                passwordretype.text?.clear()
            } else
                toast("password is not match")
        }
        view.findViewById<FloatingActionButton>(R.id.infouser_btn_update)
            .setOnClickListener {
                isEdit = !isEdit;
                applyEdit(isEdit)
                if (isEdit == false) {
                    try {
                        infomodel.info.value?.Name = name.text.toString()
                        infomodel.info.value?.PhoneNumber = phone.text.toString()
                        infomodel.info.value?.Email = cemail.text.toString()
                        infomodel.info.value?.Position = pos.text.toString()
                        infomodel.info.value?.BirthDate =
                            DateParser.parser(birthday.text.toString())

                        infomodel.info.value?.Gender = gender.text.toString()
                        infomodel.info.value?.IDCardNumber = cardid.text.toString()
                        infomodel.info.value?.IDCardCreateDate =
                            DateParser.parser(cardidprovidedate.text.toString())
                        infomodel.info.value?.IDCardCreateLocation = cardid.text.toString()
                        infomodel.updateUserInfo();
                        toast("Chỉnh sữa thông tin thành công!!!")
                    } catch (ex: Exception) {
                        toast(ex.message!!)
                    }
                }
            }
    }

    fun uploadImage() {
        Log.d("Avatar", imagepicked.value.toString() + "\n" + infomodel.info.value?.AvatarURL)
//        if (imagepicked.value?.userInfo?.equals(Uri.parse(infomodel.info.value?.AvatarURL)) == false) {

        val uuid = UUID.randomUUID();
        val ref = cloudstore.reference.child("public/avatar/av_$uuid")
        ref.putFile(imagepicked.value!!).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { uri ->
                infomodel.info.value?.AvatarURL = uri.toString()
                infomodel.updateUserInfo()
            }
        }

    }

    fun updateNewPassword(newpassword: String) {
        user?.updatePassword(newpassword)?.addOnCompleteListener {
            if (it.isSuccessful)
                toast("Password Change Success")
            else {
                toast("Password Change" + it.exception.toString())
            }
        }
    }

    fun toast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }
}