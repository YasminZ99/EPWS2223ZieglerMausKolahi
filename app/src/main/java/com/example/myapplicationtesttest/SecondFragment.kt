package com.example.myapplicationtesttest

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
            val placeQuery = binding.sucheOrt.text.toString().trim()

            // Build the query to filter contacts by place
            var query = databaseReference.orderByChild("place").equalTo(placeQuery)

            // Add additional filters based on checkbox values
            val availableQuery = binding.checkVerfuegbar.isChecked
            val waitinglistQuery = binding.checkWarte.isChecked
            val privatQuery = binding.checkPrivat.isChecked
            val gesetzlQuery = binding.checkGesetzl.isChecked
            val selbstQuery = binding.checkSelbstz.isChecked
            if (availableQuery || waitinglistQuery || privatQuery || gesetzlQuery || selbstQuery) {
                val filterConditions = mutableListOf<String>()
                if (availableQuery) filterConditions.add("v")
                if (waitinglistQuery) filterConditions.add("w")
                if (privatQuery) filterConditions.add("p")
                if (gesetzlQuery) filterConditions.add("g")
                if (selbstQuery) filterConditions.add("s")
                val filterString = filterConditions.joinToString(separator = ",")
                query = query.orderByChild("dummy").startAt(filterString).endAt(filterString + "\uf8ff")
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

                   // findNavController().navigate(R.id.action_SecondFragment_to_searchResultFragment)
                    val fragmentTransaction = requireFragmentManager().beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, searchResultsFragment)
                    fragmentTransaction.addToBackStack(R.id.SecondFragment.toString())
                    fragmentTransaction.commit()
                    requireFragmentManager().executePendingTransactions()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e(TAG, "Firebase database error: $databaseError")
                }
            })
        }
    }


}
