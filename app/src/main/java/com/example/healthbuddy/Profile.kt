package com.example.healthbuddy

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class Profile : AppCompatActivity() {
    private lateinit var Name : TextView
    private lateinit var Email : TextView
    private lateinit var Phone : TextView
    private lateinit var Ward : TextView
    private lateinit var Date : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Name = findViewById(R.id.name)
        Email = findViewById(R.id.gmail)
        Phone = findViewById(R.id.phone)
        Ward = findViewById(R.id.ward)
        Date = findViewById(R.id.date)
        val getShared : SharedPreferences = getSharedPreferences("demo", MODE_PRIVATE)
        val name = getShared.getString("name","null")
        val email = getShared.getString("email","null")
        val phone = getShared.getString("phone","null")
        val ward = getShared.getString("ward","null");
        val date = getShared.getString("dateofadmit","null")
        val detail : SharedPreferences = getSharedPreferences("bed", MODE_PRIVATE)
        val dateof = detail.getString("dateofAdmit","null")
        val wardt = detail.getString("wardType","null")
        Toast.makeText(this, "$wardt", Toast.LENGTH_SHORT).show()
        Name.setText(name)
        Email.setText(email)
        Phone.setText(phone)
        if(ward=="null"){
            Ward.setText(wardt)
        }
        else{
            Ward.setText(ward)
        }
        if(date=="null"){
            Date.setText(dateof)
        }
        else {
            Date.setText(date)
        }
    }
    fun dispatch(view: View) {
        Toast.makeText(this, "Dispatch Successfully", Toast.LENGTH_SHORT).show()
    }
}