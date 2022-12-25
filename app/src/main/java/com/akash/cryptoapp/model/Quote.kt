package com.akash.cryptoapp.model

data class Quote(
    val percentChange1h: Double,
    val percentChange24h: Double,
    val percentChange30d: Double,
    val percentChange60d: Double,
    val percentChange7d: Double,
    val percentChange90d: Double,
    val price: Double,
    val turnover: Double,
    val tvl: Double,
    val volume24h: Double,
    val ytdPriceChangePercentage: Double
)