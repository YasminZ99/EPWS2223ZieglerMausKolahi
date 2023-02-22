package com.example.myapplicationtesttest.data
import android.os.Parcel
import android.os.Parcelable

data class Contact(
    val name: String?  =null,
    val phone: String? =null,
    val place: String? =null,
    val gender: String?  =null,
    val verfuegbar: Boolean? = false,
    //
    val privatversichert: Boolean? = false,
    val selbstzahler: Boolean? = false,
    val gesetzlich: Boolean? = false,
    val Warteliste: Boolean? = false


): Parcelable { constructor(parcel: Parcel) : this(
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readByte() != 0.toByte(),
    parcel.readByte() != 0.toByte(),
    parcel.readByte() != 0.toByte(),
    parcel.readByte() != 0.toByte(),
    parcel.readByte() != 0.toByte()
)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(place)
        parcel.writeString(gender)
        parcel.writeByte(if(verfuegbar!!)1 else 0)
        parcel.writeByte(if(privatversichert!!)1 else 0)
        parcel.writeByte(if(selbstzahler!!)1 else 0)
        parcel.writeByte(if(gesetzlich!!)1 else 0)
        parcel.writeByte(if(Warteliste!!)1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(source: Parcel): Contact {
            return Contact(source)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size!!)
        }
    }

}