package com.example.myapplicationtesttest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplicationtesttest.databinding.FragmentProfilMainBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.myapplicationtesttest.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class ProfilMain : Fragment() {
    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentProfilMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfilMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

        binding.tvAusloggen.setOnClickListener {
            logout()
        }
        binding.tvFavorites.setOnClickListener{
            navController.navigate(R.id.action_profilMain_to_favoritesFragment)
        }
    }

    private fun logout() {
        mAuth.signOut()
        navController.navigate(R.id.action_profilMain_to_loginFragment2)
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance()
    }
}