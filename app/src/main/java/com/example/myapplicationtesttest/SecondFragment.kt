package com.example.myapplicationtesttest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationtesttest.data.Contact
import com.example.myapplicationtesttest.data.ContactAdapter
import com.example.myapplicationtesttest.databinding.FragmentSecondBinding
import com.google.firebase.database.*

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var contactsReference: DatabaseReference
    private val matchingContacts = mutableListOf<Contact>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)

        database = FirebaseDatabase.getInstance()
        contactsReference = database.getReference("contacts")

        binding.Searchbutton.setOnClickListener {
            matchingContacts.clear()
            searchData()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        return binding.root
    }

    private fun searchData() {
        val searchTerm = binding.editTextSucheName.text.toString().trim()
        if (searchTerm.isEmpty()) {
            return
        }

        val query = contactsReference.orderByChild("name").startAt(searchTerm).endAt(searchTerm + "\uf8ff")
        try {
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (contactSnapshot in dataSnapshot.children) {
                        val contact = contactSnapshot.getValue(Contact::class.java)
                        matchingContacts.add(contact!!)
                    }
                    val adapter = ContactAdapter(requireContext(), matchingContacts)
                    binding.recyclerView.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                }
            })
        } catch (e: Exception) {
            e.message?.let { Log.e("Error", it) }
        }
    }
}
