package com.akash.cryptoapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akash.cryptoapp.model.FavouriteCryptoCurrency

@Dao
interface FavouriteCryptoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFabCrypto(crypto:FavouriteCryptoCurrency)

    @Query("SELECT * FROM fav_crypto ORDER BY id DESC")
    fun getAllFavouriteCrypto(): LiveData<List<FavouriteCryptoCurrency>>

    @Delete
    suspend fun deleteCryptoFromWatchlist(watchCrypto:FavouriteCryptoCurrency)
}