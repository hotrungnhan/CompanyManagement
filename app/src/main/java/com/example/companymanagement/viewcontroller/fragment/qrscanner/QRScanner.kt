package com.example.companymanagement.viewcontroller.fragment.qrscanner

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.companymanagement.R
import com.google.firebase.firestore.FirebaseFirestore
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanCustomCode
import io.github.g00fy2.quickie.config.BarcodeFormat
import io.github.g00fy2.quickie.config.ScannerConfig
import io.github.g00fy2.quickie.content.QRContent


class QRScanner : Fragment() {
    val store = FirebaseFirestore.getInstance();
    var qrcode: MutableLiveData<String> = MutableLiveData()
    val scanner = registerForActivityResult(ScanCustomCode()) {
        if (it is QRResult.QRSuccess && it.content is QRContent.Plain)
            qrcode.postValue(it.content.rawValue)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return View(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        qrcode.observe(this.viewLifecycleOwner) {
            var result = Base64.decode(it, Base64.NO_PADDING or Base64.NO_WRAP)
            Log.d("QRcore", String(result).split("|")[0])
            var doc = String(result).split("|")[0];
            store.collection("checkin").document(doc).get().addOnCompleteListener {
                if (it.isSuccessful && it.result?.exists() == true){
                }
            }
        }
        scanner.launch(ScannerConfig.build {
            setBarcodeFormats(listOf(BarcodeFormat.FORMAT_QR_CODE))
            setOverlayStringRes(R.string.qrscaner_string)
            setHapticSuccessFeedback(true)
        })
    }
}