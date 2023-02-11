package com.example.myapplicationtesttest.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtesttest.R

class ContactAdapter(private val context: Context, private val contactList: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactList[position]
        holder.nameTextView.text = contact.name
        holder.phoneTextView.text = contact.phone
        holder.placeTextView.text = contact.place
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        val phoneTextView: TextView = itemView.findViewById(R.id.phone_text_view)
        val placeTextView: TextView = itemView.findViewById(R.id.phone_text_view)
    }
}