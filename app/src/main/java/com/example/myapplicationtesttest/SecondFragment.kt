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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_second, container, false)
        return view
    }

   private fun setupSearchButton() {
       binding.Searchbutton.setOnClickListener {
           Log.d("MYLOG", "Search button clicked")
           val nameQuery = binding.sucheName.text.toString().trim()
           val placeQuery = binding.sucheOrt.text.toString().trim()
           var genderQuery = ""
           if (binding.checkM.isChecked) {
               genderQuery += "m,"
           }
           if (binding.checkW.isChecked) {
               genderQuery += "f,"
           }
           if (binding.checkD.isChecked) {
               genderQuery += "d,"
           }
           if (genderQuery.endsWith(",")) {
               genderQuery = genderQuery.substring(0, genderQuery.length -1)
           }
           Log.d("MYLOG", "Name query: $nameQuery")
           Log.d("MYLOG", "Place query: $placeQuery")
           Log.d("MYLOG", "Gender query: $genderQuery")
           val availableQuery = binding.checkVerfuegbar.isChecked
           val waitinglistQuery = binding.checkWarte.isChecked
           val privatQuery = binding.checkPrivat.isChecked
           val gesetzlQuery = binding.checkGesetzl.isChecked
           val selbstQuery = binding.checkSelbstz.isChecked

           val databaseReference = FirebaseDatabase.getInstance().getReference("Contacts")
           var query = databaseReference.orderByChild("name").startAt(nameQuery).endAt(nameQuery + "\uf8ff")
           if (!placeQuery.isEmpty()) {
               query = query.orderByChild("place").equalTo(placeQuery)
           }
           if (!genderQuery.isEmpty()) {
               query = query.orderByChild("gender").equalTo(genderQuery.toString())
           }
           if (availableQuery) {
               query = query.orderByChild("verfuegbar")
           }
           if (waitinglistQuery) {
               query = query.orderByChild("warteliste")
           }
           if (privatQuery) {
               query = query.orderByChild("privatversichert")
           }
           if (gesetzlQuery) {
               query = query.orderByChild("gesetzlich")
           }
           if (selbstQuery) {
               query = query.orderByChild("selbstzahler")
           }
           Log.d("MYLOG", "Executing query: $query")

           query.addValueEventListener(object : ValueEventListener {
               override fun onDataChange(dataSnapshot: DataSnapshot) {
                   Log.d("MYLOG", "Data received from Firebase")
                   val contactList: MutableList<Contact> = ArrayList()
                   for (snapshot in dataSnapshot.children) {
                       val contact = snapshot.getValue(Contact::class.java)
                       if (contact != null) {
                           contactList.add(contact)
                       }
                   }
                   Log.d("MYLOG", "Contact list size: ${contactList.size}")

                   val searchResultsFragment = SearchResultFragment()
                   val bundle = Bundle()
                   bundle.putParcelableArrayList("contactList", ArrayList(contactList))
                   searchResultsFragment.arguments = bundle
                   Log.d("MYLOG", "Opening search results fragment")
                   val fragmentTransaction = requireFragmentManager().beginTransaction()
                   fragmentTransaction.replace(R.id.fragment_container, searchResultsFragment)
                   fragmentTransaction.addToBackStack(null)
                   fragmentTransaction.commit()
               }

               override fun onCancelled(databaseError: DatabaseError) {
                   Log.e(TAG, "Firebase database error: $databaseError")
               }
           })
       }


   }
}
