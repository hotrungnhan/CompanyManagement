package com.example.companymanagement.viewcontroller.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.activity.main.MainActivity
import com.example.companymanagement.viewcontroller.fragment.forgotpassword.ForgotPass
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //debug
        if (resources.getBoolean(R.bool.disable_login) == false) {
            finish()
            val loginintent = Intent(this, MainActivity::class.java)
            startActivity(loginintent)
        }
        setContentView(R.layout.activity_login)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val forgot = findViewById<TextView>(R.id.forgot_password)
        val pw_layout = findViewById<TextInputLayout>(R.id.password_layout)
        login.setOnClickListener {

            var username = username.text.toString()
            var password = password.text.toString()
            if (loginCondition(username, password)) {
                loading.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this) { task ->
                        loading.visibility = View.GONE
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            toastUserSucess(user!!)
                            val loginintent = Intent(this, MainActivity::class.java)
                            startActivity(loginintent)
                        } else {
                            // If sign in fails, display a message to the user.
                            showLoginFailed(task.exception?.message)
                        }
                    }
            } else {
                toastBadFormat();
            }
        }
        forgot.setOnClickListener{
            username.visibility = View.GONE
            login.visibility = View.GONE
            loading.visibility = View.GONE
            pw_layout.visibility = View.GONE
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.login_container, ForgotPass()).commit()
        }

    }

    private fun loginCondition(username: String, password: String): Boolean {

        return username.length > 5 && password.length > 5
    }

    private fun toastBadFormat() {
        Toast.makeText(
            applicationContext,
            "Wrong Username or password format",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun toastUserSucess(firebaseUser: FirebaseUser) {
        val welcome = getString(R.string.welcome)
        val displayName = firebaseUser.email
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(string: String?) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()
    }
}
