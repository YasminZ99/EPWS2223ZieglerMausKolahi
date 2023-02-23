package com.example.myapplicationtesttest

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.myapplicationtesttest.data.Contact
import com.example.myapplicationtesttest.databinding.FragmentSecondBinding
import com.google.firebase.database.*

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentSecondBinding.inflate(inflater, container, false)
        databaseReference = FirebaseDatabase.getInstance().getReference("Contacts")
        setupSearchButton()
        return binding.root

    }

    private fun setupSearchButton() {
        binding.Searchbutton.setOnClickListener {
            val placeQuery = binding.sucheOrt.text.toString().trim().lowercase()

            // Build the query to filter contacts by place
            var query = databaseReference.orderByChild("place").startAt(placeQuery).endAt(placeQuery + "\uf8ff")

            // Add additional filters based on checkbox values
            val availableQuery = binding.checkVerfuegbar.isChecked
            val waitinglistQuery = binding.checkWarte.isChecked
            val privatQuery = binding.checkPrivat.isChecked
            val gesetzlQuery = binding.checkGesetzl.isChecked
            val selbstQuery = binding.checkSelbstz.isChecked
            if (availableQuery || waitinglistQuery || privatQuery || gesetzlQuery || selbstQuery) {
                query = query.orderByChild("filters/available")
                if (availableQuery) query = query.equalTo(true)
                if (waitinglistQuery) query = query.orderByChild("filters/waitingl").equalTo(true)
                if (privatQuery) query = query.orderByChild("filters/privat").equalTo(true)
                if (gesetzlQuery) query = query.orderByChild("filters/gesetzl").equalTo(true)
                if (selbstQuery) query = query.orderByChild("filters/selbstz").equalTo(true)
            }

            // Execute the query and display the search results in a fragment
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val contactList: MutableList<Contact> = ArrayList()
                    for (snapshot in dataSnapshot.children) {
                        val contact = snapshot.getValue(Contact::class.java)
                        if (contact != null) {
                            contactList.add(contact)
                        }
                    }
                    binding.fragmentContainer.removeAllViews()
                    val searchResultsFragment = SearchResultFragment()
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("contactList", ArrayList(contactList))
                    searchResultsFragment.arguments = bundle

                    binding.sucheOrt.setVisibility(View.GONE);
                    binding.textVerfuegbar.setVisibility(View.GONE)
                    binding.checkVerfuegbar.setVisibility(View.GONE)
                    binding.checkWarte.setVisibility(View.GONE)
                    binding.textAbrechnung.setVisibility(View.GONE)
                    binding.checkGesetzl.setVisibility(View.GONE)
                    binding.checkPrivat.setVisibility(View.GONE)
                    binding.checkSelbstz.setVisibility(View.GONE)
                    binding.textGeschlecht.setVisibility(View.GONE)
                    binding.checkW.setVisibility(View.GONE)
                    binding.checkM.setVisibility(View.GONE)
                    binding.checkD.setVisibility(View.GONE)

                    fragmentManager?.commit {
                        setReorderingAllowed(true)
                        // Replace whatever is in the fragment_container view with this fragment
                        replace(R.id.fragment_container, searchResultsFragment)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e(TAG, "Firebase database error: $databaseError")
                }
            })
        }

    }


}
