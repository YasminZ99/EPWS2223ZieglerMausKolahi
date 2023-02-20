package com.example.myapplicationtesttest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.example.myapplicationtesttest.data.Contact
import com.google.firebase.database.FirebaseDatabase


/**
 * A simple [Fragment] subclass.
 * Use the [InsertFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsertFragment : Fragment() {

        private lateinit var database: FirebaseDatabase

        @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_insert, container, false)

            // Initialize Firebase Database
            database = FirebaseDatabase.getInstance()

            // Get references to the EditText views
            val nameEditText = view.findViewById<EditText>(R.id.nameText)
            val phoneEditText = view.findViewById<EditText>(R.id.phoneText)
            val placeEditText = view.findViewById<EditText>(R.id.placeText)
            val genderEditText = view.findViewById<EditText>(R.id.genderText)
            val verfuegbarSwitch = view.findViewById<Switch>(R.id.verfügbarSwitch)
            val wartelisteSwitch = view.findViewById<Switch>(R.id.wartelisteSwitch)
            val selbstSwitch = view.findViewById<Switch>(R.id.selbstzahlerSwitch)
            val gesetzlichSwitch = view.findViewById<Switch>(R.id.gestzlichSwitch)
            val privatSwitch = view.findViewById<Switch>(R.id.privatSwitch)

            // Get a reference to the button
            val submitButton = view.findViewById<Button>(R.id.submitButton)



            // Set an OnClickListener on the button
            submitButton.setOnClickListener {
                try {
                    // Get the values entered in the EditText views
                    val name = nameEditText.text.toString()
                    val phone = phoneEditText.text.toString()
                    val place = placeEditText.text.toString()
                    val gender = genderEditText.text.toString()

                    val istVerfuegbar = verfuegbarSwitch.isChecked
                    val Warteliste = wartelisteSwitch.isChecked
                    val selbstzahler = selbstSwitch.isChecked
                    val gesetzlichversicherte = gesetzlichSwitch.isChecked
                    val privatversicherte = privatSwitch.isChecked

                    val contact = Contact(name, phone,place,gender,istVerfuegbar,Warteliste,selbstzahler,gesetzlichversicherte,privatversicherte)

                    // Create a new database reference
                    val myRef = database.getReference("Contacts")
                    myRef.child(name).setValue(contact)


                    // Push the data to the database
                    myRef.push().setValue(mapOf(
                        "name" to name,
                        "phone" to phone,
                        "place" to place,
                        "gender" to gender,
                        "istVerfuegbar" to istVerfuegbar,
                        "Warteliste" to Warteliste,
                        "selbstzahler" to selbstzahler,
                        "gesetzlichversicherte" to gesetzlichversicherte,
                        "privatversicherte" to privatversicherte


                    ))
                  // myRef.child("Contacts").setValue(name);

                } catch (e: Exception) {
                    Log.e("MyFragment", "Error pushing data to the database", e)
                }
            }

            return view
        }
    }
