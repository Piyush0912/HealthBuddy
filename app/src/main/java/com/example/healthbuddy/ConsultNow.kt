package com.example.healthbuddy

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable.children
import kotlinx.coroutines.launch
class ConsultNow : AppCompatActivity() {
    private lateinit var DoctorList:ArrayList<Doctorrecord>
    private lateinit var recyclerView: RecyclerView
    private lateinit var madapter:Adapter
    private lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consult_now)
        recyclerView = findViewById(R.id.recyclerView)
        DoctorList = ArrayList()
        madapter = Adapter(DoctorList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = madapter
        getData()
    }

    private fun getData() {
        ref = FirebaseDatabase.getInstance().reference.child("Doctor");
            ref.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                  if(snapshot.exists()){
                      for (usersnapshot in snapshot.children){
                          val user = usersnapshot.getValue(Doctorrecord::class.java)
                          DoctorList.add(user!!)
                          Log.d("Bansal",user.toString())
                      }
                      recyclerView.adapter = madapter
                  }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ConsultNow, "${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
//        val options : FirebaseRecyclerOptions<Doctorrecord> = FirebaseRecyclerOptions.Builder<Doctorrecord>().setQuery(ref,Doctorrecord::class.java).build()
//        madapter = FirebaseAdapter(options)
//        recyclerView.adapter = madapter
    }

//    override fun onStart() {
//        super.onStart()
//        madapter.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        madapter.stopListening()
//    }
}