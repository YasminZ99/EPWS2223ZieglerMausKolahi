package com.example.myapplicationtesttest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtesttest.data.Contact
import com.example.myapplicationtesttest.data.ContactAdapter
import com.example.myapplicationtesttest.databinding.ActivityMainBinding
import com.example.myapplicationtesttest.databinding.FragmentSecondBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import io.reactivex.rxjava3.annotations.NonNull

class SecondFragment : Fragment() {

    private lateinit var editTextSucheName: EditText
    private lateinit var tvname: TextView
    private lateinit var tvphone: TextView
    private lateinit var SearchButton: Button
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        editTextSucheName = view.findViewById(R.id.editTextSucheName)
        //tvname = view.findViewById(R.id.tvname)
       // tvphone = view.findViewById(R.id.tvphone)
        SearchButton = view.findViewById(R.id.Searchbutton)

        database = FirebaseDatabase.getInstance().reference.child("Contacts")

        SearchButton.setOnClickListener {
            val name = editTextSucheName.text.toString()

            // Create a listener to handle the search result
            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Loop through all contacts in the database
                    for (contactSnapshot in dataSnapshot.children) {
                        val contact = contactSnapshot.getValue(Contact::class.java)
                        // If the name matches, display the contact info and return
                        if (contact?.name.toString().contains(name, ignoreCase = true)) {
                            tvname.text = contact?.name
                            tvphone.text = contact?.phone
                            return
                        }
                    }
                    // If we haven't returned by now, the name wasn't found
                    tvname.text = "Name not found"
                    tvphone.text = ""
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error
                }
            }

            // Query the database for contacts with a matching name
            val query = database.orderByChild("name").equalTo(name)
            query.addListenerForSingleValueEvent(listener)
        }

        return view
    }


}

