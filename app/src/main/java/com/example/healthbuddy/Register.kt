package com.example.healthbuddy

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.TextureView
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
     private lateinit var nameInput: EditText
     private lateinit var txtemail: EditText
     private lateinit var pwd: EditText
     private lateinit var cnfpwd: EditText
     private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        nameInput = findViewById(R.id.editText1)
        txtemail = findViewById(R.id.editText2)
        pwd = findViewById(R.id.editText3)
        cnfpwd = findViewById(R.id.editText4)
        firebaseAuth = FirebaseAuth.getInstance()
    }
    fun sign_in(view: View) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun next(view: View) {
        val email = txtemail.text.toString()
        val pass = pwd.text.toString()
        val name = nameInput.text.toString()
        val confirmPassword = cnfpwd.text.toString()
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        if(pass.length<8){
            Toast.makeText(this, "Password is minimum of 8 character", Toast.LENGTH_SHORT).show()
            return
        }
        if (pass != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }
//        val shrd : SharedPreferences = getSharedPreferences("demo", MODE_PRIVATE)
//        val editor : SharedPreferences.Editor = shrd.edit()
//        editor.putString("name",name)
//        editor.apply()
        val intent  = Intent(this,Registration2::class.java)
        intent.putExtra("name",name)
        intent.putExtra("email",email)
        intent.putExtra("password",pass)
        startActivity(intent)
    }
}