package com.example.healthbuddy

import android.app.appsearch.StorageInfo
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.credentials.Credential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var phoneNumber: EditText
    private lateinit var Password: EditText
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var compNumber: String
    private lateinit var password :String
    private lateinit var firebaseDatabase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        phoneNumber = findViewById(R.id.editText1)
        Password = findViewById(R.id.editText2)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
//        firebaseAuth.addAuthStateListener(FirebaseAuth.AuthStateListener {
//            if(firebaseAuth.currentUser!=null){
//                startActivity(Intent(this,Dashboard::class.java))
//                finish()
//            }
//        })
    }
    fun Register(view: View) {
        val intent = Intent(this,Register::class.java)
        startActivity(intent)
        finish()
    }
    fun Login(view: View) {
        val phoneNumber=phoneNumber.text.toString()
         password=Password.text.toString()
        if (phoneNumber.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        if(password.length<8){
            Toast.makeText(this, "Password is minimum of 8 character", Toast.LENGTH_SHORT).show()
            return
        }
        val cmp = "+91$phoneNumber"
        val et_email = "+91$phoneNumber"+"@gmail.com"
        firebaseAuth.signInWithEmailAndPassword(et_email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
//                   val reference : DatabaseReference = firebaseDatabase.getReference("Users")
//                    val user = firebaseAuth.currentUser
//                    val uid = user!!.uid
//                    reference.child(uid).addValueEventListener(object : ValueEventListener{
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            val user = snapshot.getValue(User::class.java)
//                            name = user!!.Name.toString()
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//
//                        }
//                    })

//                    val shrd : SharedPreferences = getSharedPreferences("demo", MODE_PRIVATE)
//                    val editor : SharedPreferences.Editor = shrd.edit()
//                    editor.putString("phone",phoneNumber)
//                    editor.putString("name",name)
//                    editor.apply()
                    val reference : DatabaseReference = firebaseDatabase.getReference("Users")
                    reference.child(cmp).get().addOnSuccessListener {
                        if(it.exists()){
                            val name = it.child("name").value.toString()
                            val email = it.child("email").value.toString()
                            val phone = it.child("phoneNumber").value.toString()
                            val ward = it.child("wardType").value.toString()
                            val dateofadmit = it.child("dateofAdmit").value.toString()
//                Toast.makeText(this, "hii$name", Toast.LENGTH_SHORT).show()
                            val shrd : SharedPreferences = getSharedPreferences("demo", MODE_PRIVATE)
                            val editor : SharedPreferences.Editor = shrd.edit()
                            editor.putString("phone",phoneNumber)
                            editor.putString("name",name)
                            editor.putString("email",email)
                            editor.putString("ward",ward)
                            editor.putString("dateofadmit",dateofadmit)
                            editor.apply()
                        }
                        else{
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(this, "Log in Successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,Dashboard:: class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Email or password is incorrect ", Toast.LENGTH_SHORT).show()
                }
    }
    }
}