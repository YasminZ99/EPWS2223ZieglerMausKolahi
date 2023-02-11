package com.example.myapplicationtesttest

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplicationtesttest.databinding.FragmentSecondBinding
import android.widget.Toast.makeText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationtesttest.data.Contact
import com.example.myapplicationtesttest.data.ContactAdapter
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var contactsReference: DatabaseReference
    private lateinit var adapter: ContactAdapter
    private var matchingContacts = mutableListOf<Contact>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()
        contactsReference = database.reference.child("contacts")

        // Set the layout manager for the recycler view
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.Searchbutton.setOnClickListener {
            searchData()
        }

        return binding.root
    }

    private fun searchData() {
        val searchInput = binding.editTextSucheName.text.toString().trim()

        if (searchInput.isEmpty()) {
            return
        }

        contactsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                matchingContacts.clear()
                for (contactSnapshot in snapshot.children) {
                    val contact = contactSnapshot.getValue(Contact::class.java)
                    if (contact != null && contact.name!!.contains(searchInput, ignoreCase = true)) {
                        matchingContacts.add(contact)
                    }
                }
                adapter = ContactAdapter(requireContext(), matchingContacts)
                binding.recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
