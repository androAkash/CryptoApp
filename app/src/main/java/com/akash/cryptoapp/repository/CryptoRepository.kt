package com.akash.cryptoapp.repository

import com.akash.cryptoapp.api.CryptoApiService
import com.akash.cryptoapp.db.FavouriteCryptoDatabase
import com.akash.cryptoapp.model.CryptoCurrency
import com.akash.cryptoapp.model.FavouriteCryptoCurrency
import javax.inject.Inject
class CryptoRepository @Inject constructor(private val apiService: CryptoApiService, private val db : FavouriteCryptoDatabase) {
    suspend fun getCrypto() = apiService.getCrypto()

    suspend fun addCrypto(crypto: FavouriteCryptoCurrency) = db.getFabCryptoDao().addFabCrypto(crypto)

    suspend fun deleteCrypto(crypto: FavouriteCryptoCurrency) = db.getFabCryptoDao().deleteCryptoFromWatchlist(crypto)

    fun getAllFavCrypto()= db.getFabCryptoDao().getAllFavouriteCrypto()
}