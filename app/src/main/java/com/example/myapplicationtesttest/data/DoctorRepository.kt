package com.example.myapplicationtesttest.data

import androidx.lifecycle.LiveData

class DoctorRepository (private val doctorsDAO: DoctorsDAO) {

    val readAllData: LiveData<List<Doctors>> = doctorsDAO.readAllData()

    suspend fun addDoctor(doctor: Doctors){
        doctorsDAO.addDoctor(doctor)
    }
}