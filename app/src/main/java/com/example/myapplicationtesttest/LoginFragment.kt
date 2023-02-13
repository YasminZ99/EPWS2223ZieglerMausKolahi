package com.example.myapplicationtesttest

import com.example.myapplicationtesttest.databinding.FragmentLoginBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import androidx.navigation.Navigation

class LoginFragment : Fragment () {

    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

        binding.textSignUp.setOnClickListener {
            navController.navigate(R.id.action_loginFragment2_to_ProfilFragment)
        }

        binding.LoginB.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val pass = binding.passwLogin.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty())

                loginUser(email, pass)
            else
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass)/*.addOnCompleteListener {
             if (it.isSuccessful)
                navController.navigate(R.id)
              else
                  Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()

          }*/
    }

    private fun init(view: View) {
              navController = Navigation.findNavController(view)
              mAuth = FirebaseAuth.getInstance()
    }

}