package com.example.myapplicationtesttest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Doctors::class], version = 1, exportSchema = false)
abstract class DoctorDatabase : RoomDatabase() {

    abstract fun doctorDao(): DoctorsDAO

    companion object{
        @Volatile
        private var INSTANCE: DoctorDatabase? = null

        fun getDatabase(context: Context): DoctorDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DoctorDatabase::class.java,
                    "doctor_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}