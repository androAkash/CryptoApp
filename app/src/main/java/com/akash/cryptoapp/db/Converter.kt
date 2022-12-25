package com.akash.cryptoapp.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.akash.cryptoapp.model.Quote
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters() {
    var gson = Gson()

    @TypeConverter
    fun quoteItemToString(quoteString:List<Quote>):String{
        return gson.toJson(quoteString)
    }

    @TypeConverter
    fun stringToQuoteItem(data : String):List<Quote>{
        val listType = object : TypeToken<List<Quote>>(){

        }.type
        return gson.fromJson(data,listType)
    }

}
