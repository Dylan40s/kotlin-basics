package com.example.myworklifeapp.Network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.IOException
import java.util.*

class DateAsMillisecondsFromEpochJsonAdapter : JsonAdapter<Date>() {

    @Synchronized
    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: Date?) {
        val string = if (value == null) null else value.time.toString()
        writer.value(string)
    }

    @Synchronized
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Date? {
        val string = reader.nextString()
        return Date(string.toLong())
    }

}