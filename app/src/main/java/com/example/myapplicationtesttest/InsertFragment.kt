package com.example.myapplicationtesttest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myapplicationtesttest.data.Contact
import com.example.myapplicationtesttest.databinding.FragmentFirstBinding
import com.example.myapplicationtesttest.databinding.FragmentInsertBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 * Use the [InsertFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsertFragment : Fragment() {

    private lateinit var binding: FragmentInsertBinding
    private lateinit var dbRef: DatabaseReference
    val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

        dbRef = database.getReference("Contacts")
        binding.buttonSave.setOnClickListener {
            saveData()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_insert, container, false)
    }

    private fun saveData() {
        val name = binding.editName.text.toString()
        val phone = binding.editPhone.text.toString()

        if (name.isEmpty()) {
            binding.editName.error = "please enter name"
        }
        if (phone.isEmpty()) {
            binding.editPhone.error = "please enter phone"
        }
        val id = dbRef.push().key!!
        val contact = Contact(id, name, phone)
        dbRef.child(id).setValue(contact).addOnCompleteListener {
//            Toast.makeText(this, "Data inserted succesfully", Toast.LENGTH_LONG).show()
//        }.addOnFailureListener { err ->
//            Toast.makeText(
//                this,
//                "Error ${err.message}",
//                Toast.LENGTH_LONG
//            ).show()
//        }
        }
    }}
