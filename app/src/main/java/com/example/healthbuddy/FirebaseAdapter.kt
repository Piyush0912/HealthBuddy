package com.example.healthbuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class FirebaseAdapter(options: FirebaseRecyclerOptions<Doctorrecord>) :
    FirebaseRecyclerAdapter<Doctorrecord, FirebaseAdapter.DoctorViewHolder>(options) {
    class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val docimg : ImageView = itemView.findViewById(R.id.ivDoctorImage)
        val location : TextView = itemView.findViewById(R.id.tvLocation)
        val doctorName : TextView = itemView.findViewById(R.id.tvDoctorName)
        val specialization : TextView = itemView.findViewById(R.id.tvSpecialization)
        val post : TextView = itemView.findViewById(R.id.tvPost)
        val speaks : TextView = itemView.findViewById(R.id.tvLanguage)
        val experience : TextView = itemView.findViewById(R.id.tvExperience)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return DoctorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int, model: Doctorrecord) {
        val record : Doctorrecord = model
        holder.doctorName.text = record.Name
        holder.location.text = record.Location
        holder.specialization.text = record.Specialization
        holder.post.text = record.Post
        holder.speaks.text = record.Speak
        holder.experience.text = record.Experience
        Glide.with(holder.itemView.context).load(record.DoctorImg).into(holder.docimg)
    }
}