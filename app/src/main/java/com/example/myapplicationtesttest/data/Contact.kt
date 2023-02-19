package com.example.myapplicationtesttest.data

data class Contact(
    val name: String? = null,
    val phone: String?=null,

) { constructor() : this("","")}