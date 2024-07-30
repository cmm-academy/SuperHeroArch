package com.mstudio.superheroarch

import androidx.room.TypeConverter
import com.google.gson.Gson

class UbicationConverter {

    @TypeConverter
    fun fromUbication(ubication: Ubication?): String? {
        return Gson().toJson(ubication)
    }

    @TypeConverter
    fun toUbication(ubicationString: String?): Ubication? {
        return Gson().fromJson(ubicationString, Ubication::class.java)
    }
}
