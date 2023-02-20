package com.example.myapplicationtesttest

import android.R.attr.button
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtesttest.data.Contact
import com.example.myapplicationtesttest.data.ContactAdapter
import com.google.firebase.database.*
import androidx.appcompat.widget.Toolbar



class ShowContacts : AppCompatActivity() {


    private lateinit var dbref : DatabaseReference
    private lateinit var contactsRecyclerview: RecyclerView
    private lateinit var contactArrayList: ArrayList<Contact>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_contacts)



        contactsRecyclerview = findViewById(R.id.recyclerView)
        contactsRecyclerview.layoutManager = LinearLayoutManager(this)
        contactsRecyclerview.setHasFixedSize(true)
        contactArrayList = arrayListOf()

        // Funktionsaufruf DB Daten beschaffen
        getUserData()



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Contacts")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (contactSnapshot in snapshot.children) {
                        val contact = contactSnapshot.getValue(Contact::class.java) // gezogenes Objekt in Variable user einfügen
                        contactArrayList.add(contact!!) //darf nicht null sein
                    }

                    contactsRecyclerview.adapter = ContactAdapter(contactArrayList)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", "onCancelled ${error.message}" )
            }
        } )
    }
}


