package com.example.myapplicationtesttest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplicationtesttest.data.DoctorViewModel
import com.example.myapplicationtesttest.data.Doctors
import com.example.myapplicationtesttest.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

/////////////////Hier muss noch gearbeitet werden -> Das ViewModel für die DB muss implementiert werden
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private lateinit var mDoctorViewModel : DoctorViewModel
    private lateinit var addDataButton: Button



//     This property is only valid between onCreateView and
//     onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.fragment_second,container,false)
        _binding = FragmentSecondBinding.inflate(inflater, container, false)


        //Diese zeile bringt die App zum abstürzen
        mDoctorViewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)



//        return view
        return binding.root

    }

    //Eigener Code//
    private fun insertDataToDatabase(){
        val data = Doctors(0,"a","12","haus")
        //Add Data to Database
        mDoctorViewModel.addDoctor(data)
    }
    //Bis Hier//

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        //Aktion: Fügt daten der datenbank hinzu
        binding.addDataButton.setOnClickListener{
            insertDataToDatabase()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}