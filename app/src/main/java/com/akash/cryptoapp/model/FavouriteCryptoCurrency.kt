package com.akash.cryptoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_crypto")
data class FavouriteCryptoCurrency(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val slug: String,
    val symbol: String,
    val quotes: List<Quote>,
    val totalSupply: Double
)