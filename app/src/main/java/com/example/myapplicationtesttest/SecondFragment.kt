package com.example.myapplicationtesttest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplicationtesttest.data.Contact
import com.example.myapplicationtesttest.databinding.FragmentSecondBinding
import com.google.firebase.database.*

class SecondFragment : Fragment() {

    private lateinit var sucheName: EditText
    private lateinit var tvname: TextView
    private lateinit var tvphone: TextView
    private lateinit var SearchButton: Button
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        val binding: FragmentSecondBinding


        //tvname = view.findViewById(R.id.tvname)
       // tvphone = view.findViewById(R.id.tvphone)
        SearchButton = view.findViewById(R.id.Searchbutton)

        database = FirebaseDatabase.getInstance().reference.child("Contacts")

        SearchButton.setOnClickListener {
            val name = sucheName.text.toString()

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

