package com.example.myapplicationtesttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplicationtesttest.data.Contact
import com.example.myapplicationtesttest.databinding.ActivityMainBinding
import com.example.myapplicationtesttest.databinding.ActivityInsertionBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class insertionActivity : AppCompatActivity() {




    private lateinit var binding: ActivityInsertionBinding
    private lateinit var dbRef: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        dbRef = FirebaseDatabase.getInstance().getReference("Contacts")
        binding.button.setOnClickListener {
            saveData()
        }

    }
    private fun saveData() {
        val name = binding.editName.text.toString()
        val phone = binding.editPhone.text.toString()

        if(name.isEmpty()) {
            binding.editName.error = "please enter name"
        }
        if(phone.isEmpty()) {
            binding.editPhone.error = "please enter phone"
        }
        val id = dbRef.push().key!!
        val contact = Contact(id,name,phone)
        dbRef.child(id).setValue(contact).addOnCompleteListener{
            Toast.makeText(this,"Data inserted succesfully",Toast.LENGTH_LONG).show()
        }.addOnFailureListener { err ->Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()  }
    }
}