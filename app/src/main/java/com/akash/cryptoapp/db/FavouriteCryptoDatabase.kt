package com.akash.cryptoapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akash.cryptoapp.model.FavouriteCryptoCurrency
import retrofit2.Converter

@Database(entities = [FavouriteCryptoCurrency::class], version = 1)
@TypeConverters(Converters::class)
abstract class FavouriteCryptoDatabase : RoomDatabase() {
    abstract fun getFabCryptoDao():FavouriteCryptoDao
}