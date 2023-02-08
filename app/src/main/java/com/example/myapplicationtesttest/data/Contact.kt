package com.example.myapplicationtesttest.data

class Contact (var name: String? = null, var phoneNumber: String? = null) {

    fun toMap(): Map<String, Any?> {
        val result = HashMap<String, Any?>()
        result["name"] = name
        result["phoneNumber"] = phoneNumber

        return result
    }
}