package com.example.myapplicationtesttest.data

data class Contact(
    val name: String?  =null,
    val phone: String? =null,
    val place: String? =null,
    val gender: String?  =null,
    val privatversichert: Boolean? = null,
    val selbstzahler: Boolean? = null,
    val gesetzlich: Boolean? = null,
    val verfuegbar: Boolean? = null,
    val Warteliste: Boolean? = null


) { constructor() : this(null, null, null, null, null, null, null, null,null) }