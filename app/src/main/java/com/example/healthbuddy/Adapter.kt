package com.example.healthbuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.concurrent.fixedRateTimer

class Adapter(private val DoctorList: ArrayList<Doctorrecord>): RecyclerView.Adapter<Adapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val record : Doctorrecord = DoctorList[position]
        holder.doctorName.text = record.Name
        holder.location.text = record.Location
        holder.specialization.text = record.Specialization
        holder.post.text = record.Post
        holder.speaks.text = record.Speak
        holder.experience.text = record.Experience.toString()
        Glide.with(holder.itemView.context).load(record.DoctorImg).into(holder.docimg)
    }
    override fun getItemCount(): Int {
        return DoctorList.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val docimg : ImageView = itemView.findViewById(R.id.ivDoctorImage)
        val location :  TextView = itemView.findViewById(R.id.tvLocation)
        val doctorName : TextView = itemView.findViewById(R.id.tvDoctorName)
        val specialization : TextView = itemView.findViewById(R.id.tvSpecialization)
        val post : TextView = itemView.findViewById(R.id.tvPost)
        val speaks : TextView = itemView.findViewById(R.id.tvLanguage)
        val experience : TextView = itemView.findViewById(R.id.tvExperience)
    }
}