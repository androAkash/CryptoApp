package com.akash.cryptoapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.cryptoapp.model.CryptoCurrency
import com.akash.cryptoapp.model.CryptoResponse
import com.akash.cryptoapp.model.FavouriteCryptoCurrency
import com.akash.cryptoapp.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale.filter
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {
    private val _response = MutableLiveData<CryptoResponse>()
    val cryptoResponse: LiveData<CryptoResponse>
        get() = _response

    init {
        getCryptos()
    }

    private fun getCryptos() = viewModelScope.launch {
        repository.getCrypto().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body())
            } else {
                Log.d("TAG", "getCryptos: ${response.code()}")
            }
        }
    }

    fun addFavCrypto(crypto:FavouriteCryptoCurrency) = viewModelScope.launch {
        repository.addCrypto(crypto)
    }

    fun deleteCrypto(crypto: FavouriteCryptoCurrency) = viewModelScope.launch {
        repository.deleteCrypto(crypto)
    }

    fun getAllFavCryptos() = repository.getAllFavCrypto()
}