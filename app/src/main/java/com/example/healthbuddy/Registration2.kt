package com.example.healthbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.chaos.view.PinView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class Registration2 : AppCompatActivity() {
    private lateinit var etPhoneNumber : TextInputEditText
    private lateinit var textInputLayout : TextInputLayout
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration2)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        textInputLayout = findViewById(R.id.tilPhoneNumber)
        email = intent.getStringExtra("email").toString()
        password = intent.getStringExtra("password").toString()
        name = intent.getStringExtra("name").toString()
    }
    fun getcode(view: View) {
        val number = etPhoneNumber.text!!.trim().toString()
        if (number.length != 10) {
            textInputLayout.error = "Please Enter Valid Phone Number"
            textInputLayout.requestFocus()
        } else {
            val phoneNumber = "+91$number"
            val intent = Intent(this,Verification::class.java)
            intent.putExtra("phoneNumber",phoneNumber)
            intent.putExtra("name",name)
            intent.putExtra("email",email)
            intent.putExtra("password",password)
            startActivity(intent)
        }
    }
}