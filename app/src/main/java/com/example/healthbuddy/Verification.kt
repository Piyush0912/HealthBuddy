package com.example.healthbuddy

import android.app.Activity
import android.content.ComponentCallbacks
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.TimeUnit
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.arch.core.executor.TaskExecutor
import androidx.core.widget.addTextChangedListener
import com.chaos.view.PinView
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors.MAIN_THREAD
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.ref.Reference
import javax.sql.StatementEvent
import kotlin.time.DurationUnit

class Verification : AppCompatActivity() {
    private lateinit var etCode: PinView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var phoneNumber: String
    private lateinit var pinView: PinView
    private lateinit var verificationID : String
    private lateinit var email: String
    private lateinit var password:String
    private lateinit var name: String
//    private lateinit var sharedPreference : SharedPreferences
    private lateinit var reference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var editor : SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
//         sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//         editor = sharedPreference.edit()
        firebaseAuth = FirebaseAuth.getInstance()
        etCode = findViewById(R.id.etCode)
        pinView = findViewById(R.id.etCode)
        pinView = findViewById(R.id.etCode)
        phoneNumber = intent.getStringExtra("phoneNumber").toString()
        email = intent.getStringExtra("email").toString()
        name = intent.getStringExtra("name").toString()
        password = intent.getStringExtra("password").toString()
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.

            val code = credential.smsCode
            if(code!=null){
                pinView.setText(code)
            }
        }
        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {

            } else if (e is FirebaseTooManyRequestsException) {

            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.

            // Save verification ID and resending token so we can use them later
         verificationID = verificationId
            Toast.makeText(this@Verification, "Verification code sent", Toast.LENGTH_SHORT).show()
        }
    }
    private fun storeNewUSerData() {
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("Users")
        val user = User(name,email,password,phoneNumber)
        reference.child(phoneNumber).setValue(user)
//        editor.putString("userid", firebaseAuth.currentUser?.uid)
//        editor.putString("name",name)
//        editor.putString("email",email)
//        editor.apply()
    }
     fun verify(view: View) {
        val typeotp = etCode.text.toString()
        if(typeotp.isNotEmpty()){
            if(typeotp.length!=0){
                val phoneEmail = phoneNumber+"@gmail.com"
                firebaseAuth.createUserWithEmailAndPassword(phoneEmail, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            storeNewUSerData()
                            Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
        }
    }
}