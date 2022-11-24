package com.example.healthbuddy

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class BedAvailability : AppCompatActivity() {
    private lateinit var item: String
    private lateinit var available : TextInputEditText
    private lateinit var items : Array<String>
    private lateinit var btnDate : TextInputLayout
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var rootDatabaseReference : DatabaseReference
    private lateinit var Available : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bed_availability)
        available = findViewById(R.id.et_model)
        btnDate  = findViewById(R.id.til_car_number)
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        rootDatabaseReference = firebaseDatabase.getReference("Users")
        items = arrayOf("CAUSALITY","GENERAL WARD","SEMI-SPECIAL ROOM","SPECIAL WARDS","DELUXE ROOM","CCU","ICCU","SICU","BURN WARD","NICU","PICU")
        autoCompleteTextView = findViewById(R.id.AutoText)
        adapterItems = ArrayAdapter(this,R.layout.list_item,items)
        autoCompleteTextView.setAdapter(adapterItems)
        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                item = parent.getItemAtPosition(position).toString()
//                Toast.makeText(this, "Item$item", Toast.LENGTH_SHORT).show()
//                val reference : Task<DataSnapshot> = firebaseDatabase.getReference("Hospital").get().addOnSuccessListener {
//                    if(it.exists()){
//                        val available = it.child("item").value.toString()
//                        Toast.makeText(this, "$available", Toast.LENGTH_SHORT).show()
//                    }
//                    else{
//                        Toast.makeText(this, "Bed not found", Toast.LENGTH_SHORT).show()
//                    }
//                }.addOnFailureListener {
//                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
//                }
                val reference : DatabaseReference = firebaseDatabase.getReference("Hospital")
                reference.child(item).get().addOnSuccessListener {
                    if(it.exists()){
                         Available = it.value.toString()
//                        Toast.makeText(this, "$Available", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "Bed not found", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
                }
            }
    fun Show(view: View) {
        available.setText(Available)
    }
}