package com.akash.cryptoapp.api

import com.akash.cryptoapp.model.CryptoCurrency
import com.akash.cryptoapp.model.CryptoResponse
import com.akash.cryptoapp.model.Quote
import retrofit2.Response
import retrofit2.http.GET

interface CryptoApiService {
    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getCrypto(): Response<CryptoResponse>

    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getSort():Response<CryptoCurrency>
}