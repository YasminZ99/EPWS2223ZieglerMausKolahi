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

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance()
        initRecyclerView()
        binding.Searchbutton.setOnClickListener {
            searchData()
        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun searchData() {
        val searchTerm = binding.editTextSucheName.text.toString()
        val matchingContacts = mutableListOf<Contact>()

        val reference = database.getReference("contacts")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val contact = snapshot.getValue(Contact::class.java)
                    if (contact != null && contact.name!!.contains(searchTerm)) {
                        matchingContacts.add(contact)
                    }
                }
                updateRecyclerView(matchingContacts)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("SearchFragment", "Failed to read value.", error.toException())
            }
        })
    }

    private fun updateRecyclerView(matchingContacts: List<Contact>) {
        val adapter = ContactAdapter(requireContext(), matchingContacts)
        binding.recyclerView.adapter = adapter
    }
}
