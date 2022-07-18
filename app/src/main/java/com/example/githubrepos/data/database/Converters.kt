package com.example.githubrepos.data.database

import androidx.room.TypeConverter
import com.example.githubrepos.data.model.Owner
import com.google.gson.Gson

class OwnerConverter {
    @TypeConverter
    fun ownerTypeToString(value: Owner): String =
        Gson().toJson(value)

    @TypeConverter
    fun stringToOwnerType(serializedOwner: String): Owner =
        Gson().fromJson(serializedOwner, Owner::class.java)
}