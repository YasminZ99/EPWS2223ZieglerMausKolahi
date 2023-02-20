package com.example.myapplicationtesttest.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtesttest.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ContactAdapter(private val data: ArrayList<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        val phoneTextView: TextView = itemView.findViewById(R.id.phone_text_view)
        val placeTextView: TextView = itemView.findViewById(R.id.place_text_view)
        val genderTextView: TextView = itemView.findViewById(R.id.gender_text_view)
        val privatCheck : Switch = itemView.findViewById(R.id.checkPrivat)
        val selbstCheck : Switch = itemView.findViewById(R.id.checkSelbst)
        val gesetzCheck : Switch = itemView.findViewById(R.id.checkGesetzlich)
        val verfuegbarCheck : Switch = itemView.findViewById(R.id.checkVerfuegbar)
        val wartelisteCheck : Switch = itemView.findViewById(R.id.checkWarteliste)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = data[position]
        holder.nameTextView.text = contact.name
        holder.phoneTextView.text = contact.phone
        holder.genderTextView.text = contact.gender
        holder.placeTextView.text = contact.place
        holder.verfuegbarCheck.isChecked = contact.verfuegbar == true
        holder.wartelisteCheck.isChecked = contact.Warteliste == true
        holder.gesetzCheck.isChecked = contact.gesetzlich == true
        holder.selbstCheck.isChecked = contact.selbstzahler == true
        holder.privatCheck.isChecked = contact.privatversichert == true

    }

    override fun getItemCount() = data.size
}