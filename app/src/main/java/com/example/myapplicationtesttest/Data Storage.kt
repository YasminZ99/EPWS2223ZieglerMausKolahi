package com.example.myapplicationtesttest
import com.google.gson.Gson
import android.content.Context
import com.example.myapplicationtesttest.data.Contact
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class DataStorage {
    private val gson = Gson()

    fun saveData1(context: Context, data: Contact) {
        val dataJson = gson.toJson(data)
        val file = File(context.filesDir, "data.json")
        val fos = FileOutputStream(file)
        fos.write(dataJson.toByteArray())
        fos.close()
    }

    fun loadData(context: Context): Contact {
        val file = File(context.filesDir, "data.json")
        val fis = FileInputStream(file)
        val inputString = fis.bufferedReader().use { it.readText() }
        return gson.fromJson(inputString, Contact::class.java)
    }

    fun searchData(data: Contact, value: String): Boolean {
        return data.name == value
    }
}
