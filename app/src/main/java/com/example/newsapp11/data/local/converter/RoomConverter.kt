package com.example.newsapp11.data.local.converter

import androidx.room.TypeConverter
import com.example.newsapp11.data.remote.model.Source

class RoomConverter {

    @TypeConverter
    fun convertSourceToString(source: Source): String {

        var id = "null"
        var name = "null"

        if (source.id != null) {
            id = source.id
        }
        if (source.name != null) {
            name = source.name
        }
        return "${source.id}`~`${source.name}"
    }

    @TypeConverter
    fun convertStringToSource(source: String): Source {
        var id: String? = null
        var name: String? = null

        val list =source.split("`~`")

        if(list[0] != "null"){
            id = list[0]
        }
        if(list[1] != "null"){
            name = list[1]
        }
        return Source(
            id = id,
            name = name
        )
    }
}