package com.example.myapplicationtesttest.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtesttest.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ContactAdapter(options: FirebaseRecyclerOptions<Contact>) :
    FirebaseRecyclerAdapter<Contact, ContactAdapter.ContactViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int, model: Contact) {
        holder.bind(model)
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        private val phoneTextView = itemView.findViewById<TextView>(R.id.phoneTextView)
        private val placeTextView = itemView.findViewById<TextView>(R.id.placeTextView)

        fun bind(contact: Contact) {
            nameTextView.text = contact.name
            phoneTextView.text = contact.phone
            placeTextView.text = contact.place
        }
    }
}
