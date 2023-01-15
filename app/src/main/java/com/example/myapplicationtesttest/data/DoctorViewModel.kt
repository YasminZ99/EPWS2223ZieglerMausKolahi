package com.example.myapplicationtesttest.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// Provides Data to the UI and acts as a communication center between Datenbank and UI
class DoctorViewModel(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<Doctors>>
    private val repository: DoctorRepository

    init {
        val doctorDao = DoctorDatabase.getDatabase(application).doctorDao()
        repository = DoctorRepository(doctorDao)
        readAllData = repository.readAllData
    }

    fun addDoctor(doctors: Doctors){
        viewModelScope.launch (Dispatchers.IO){
            repository.addDoctor(doctors)
        }
    }

}