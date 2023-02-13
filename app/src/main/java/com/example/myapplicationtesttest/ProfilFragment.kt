package com.example.myapplicationtesttest

import android.os.Bundle
import android.widget.Toast
import com.example.myapplicationtesttest.databinding.FragmentProfilBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation

class ProfilFragment : Fragment () {

    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentProfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init(view)
        binding.textLogin.setOnClickListener {
            navController.navigate(R.id.action_ProfilFragment_to_loginFragment2)
        }

        binding.SignUpB.setOnClickListener {
            val email = binding.emailSignUp.text.toString()
            val pass = binding.passwSignUp.text.toString()
            val verifyPass = binding.passw2SignUp.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()) {
                if (pass == verifyPass) {

                    registerUser(email, pass)

                } else {
                    Toast.makeText(context, "Passwörter stimmen nicht überein", Toast.LENGTH_SHORT).show()
                }
            } else
                Toast.makeText(context, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show()
        }

    }

   private fun registerUser(email: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful)
                navController.navigate(R.id.action_ProfilFragment_to_profilMain)
            else
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()

        }
   }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance()
    }
}