package com.example.myapplicationtesttest

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplicationtesttest.databinding.FragmentFirstBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var mAuth: FirebaseAuth


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAuth = FirebaseAuth.getInstance()
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Wechsel zum Such Bildschirm
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_insertFragment)
        }
        // Wechsel zum Profilbildschirm
        binding.ProfilButton.setOnClickListener {
            if (mAuth.currentUser != null) {
               findNavController().navigate(R.id.action_FirstFragment_to_profilMain)
            } else {
               findNavController().navigate(R.id.action_FirstFragment_to_profilFragment)
            }

        }
            binding.buttonShow.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_showContacts)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}