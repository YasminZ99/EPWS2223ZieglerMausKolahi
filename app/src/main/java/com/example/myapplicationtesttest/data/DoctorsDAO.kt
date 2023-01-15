package com.example.myapplicationtesttest.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


// DAO = DATA Access Object -> wird benötigt um mit der Datenbank zu interagieren
@Dao
interface DoctorsDAO {

    @Insert
    suspend fun addDoctor(doctor: Doctors)

    @Query("Select * From doctor_table Order BY Name ASC")
    fun readAllData(): LiveData<List<Doctors>>
}