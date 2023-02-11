package com.example.myapplicationtesttest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplicationtesttest.databinding.FragmentSecondBinding
import com.google.firebase.database.DatabaseReference
import android.widget.Toast.makeText
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var database: DatabaseReference

    //     This property is only valid between onCreateView and
//     onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.fragment_second,container,false)
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        //search for Keywords
        binding.Searchbutton.setOnClickListener {
            val string: String = binding.editTextSucheName.text.toString()
            if (string.isNotEmpty()) {
                readData(string)
            }
        }

        fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }

    fun readData(string: String) {

        database = FirebaseDatabase.getInstance().getReference("Contacts")
        database.child(string).get().addOnSuccessListener {
            if (it.exists()) {
                val name = it.child("name").value
                val phone = it.child("phone").value

                binding.editTextSucheName.text.clear()
                binding.tvName.text = name.toString()
                binding.tvPhone.text = phone.toString()
            }
            //  else Toast.makeText(this,"no matching results",Toast.LENGTH_SHORT).show()
        }
    }
}
