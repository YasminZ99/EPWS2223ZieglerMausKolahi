package com.example.myapplicationtesttest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtesttest.data.Contact
import com.example.myapplicationtesttest.data.ContactAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query


class SecondFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchEditText = view.findViewById(R.id.editTextSucheName)

        databaseReference = FirebaseDatabase.getInstance().reference.child("contacts")

        val query = databaseReference.orderByChild("name")

        val options = FirebaseRecyclerOptions.Builder<Contact>()
            .setQuery(query, Contact::class.java)
            .build()

        adapter = ContactAdapter(options)
        recyclerView.adapter = adapter

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = searchEditText.text.toString().trim()
                searchContacts(searchText)
            }
        })

        return view
    }

    private fun searchContacts(query: String) {
        val firebaseSearchQuery: Query = databaseReference
            .orderByChild("name")
            .startAt(query)
            .endAt(query + "\uf8ff")

        val firebaseRecyclerOptions: FirebaseRecyclerOptions<Contact> =
            FirebaseRecyclerOptions.Builder<Contact>()
                .setQuery(firebaseSearchQuery, Contact::class.java)
                .build()

        adapter = ContactAdapter(firebaseRecyclerOptions)
        recyclerView.adapter = adapter
        adapter.startListening()
    }

}


