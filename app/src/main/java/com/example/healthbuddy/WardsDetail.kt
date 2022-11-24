package com.example.healthbuddy

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class WardsDetail : AppCompatActivity() {
    private lateinit var item: String
    private lateinit var date : TextInputEditText
    private lateinit var items : Array<String>
    private lateinit var btnDate : TextInputLayout
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>
    private lateinit var myCalander: Calendar
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var user : FirebaseUser
    private lateinit var rootDatabaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wards_detail)
        date = findViewById(R.id.et_model)
        btnDate  = findViewById(R.id.til_car_number)
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        rootDatabaseReference = firebaseDatabase.getReference("Users")
        items = arrayOf("CAUSALITY ","GENERAL WARD","SEMI-SPECIAL ROOM","SPECIAL WARDS","DELUXE ROOM","CCU","ICCU","SICU","BURN WARD","NICU","PICU")
        autoCompleteTextView = findViewById(R.id.AutoText)
        adapterItems = ArrayAdapter(this,R.layout.list_item,items)
        autoCompleteTextView.setAdapter(adapterItems)
        autoCompleteTextView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                 item = parent.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext , "Item$item", Toast.LENGTH_SHORT).show()
            }
         myCalander = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayofMonth ->
            myCalander.set(Calendar.YEAR,year)
            myCalander.set(Calendar.MONTH,month)
            myCalander.set(Calendar.DAY_OF_MONTH,dayofMonth)
            updateLabel(myCalander)
        }
        date.setOnClickListener {
            DatePickerDialog(this,datePicker,myCalander.get(Calendar.YEAR),myCalander.get(Calendar.MONTH),myCalander.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
    private fun updateLabel(myCalander: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        date.setText(sdf.format(myCalander.time))
    }
    fun Submit(view: View) {
        val reference = firebaseDatabase.getReference("Users")
        val dateofAdmit = date.text.toString()
        val getShared : SharedPreferences = getSharedPreferences("demo", MODE_PRIVATE)
        val phoneNumber = getShared.getString("phone","NULL")
        val pqr : SharedPreferences = getSharedPreferences("bed", MODE_PRIVATE)
        val editor : SharedPreferences.Editor = pqr.edit()
        editor.putString("wardType",item)
        editor.putString("dateofAdmit",dateofAdmit)
        editor.apply()
        val cmp = "+91$phoneNumber"
        reference.child(cmp).child("wardType").setValue(item)
        reference.child(cmp).child("dateofAdmit").setValue(dateofAdmit)
        Toast.makeText(this, "Bed allotted Successfully", Toast.LENGTH_SHORT).show()
    }
}