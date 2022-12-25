package com.akash.cryptoapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.cryptoapp.adapter.MarketAdapter
import com.akash.cryptoapp.databinding.FragmentMarketBinding
import com.akash.cryptoapp.di.AppModule
import com.akash.cryptoapp.model.CryptoCurrency
import com.akash.cryptoapp.viewModel.CryptoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class MarketFragment : Fragment() {
    private lateinit var binding: FragmentMarketBinding
    private lateinit var viewModel : CryptoViewModel
    private lateinit var cryptoAdapter : MarketAdapter
    lateinit var searchText : String
    private lateinit var list: List<CryptoCurrency>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(layoutInflater)

        list = listOf()

        cryptoAdapter = MarketAdapter("market")
        binding.currencyRecyclerView.adapter = cryptoAdapter

        viewModel = ViewModelProvider(requireActivity()).get(CryptoViewModel::class.java)

        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val res = AppModule.providesRetrofitInstance().getCrypto()
            if (res.body() != null){
                withContext(Dispatchers.Main){
                    list = res.body()!!.data.cryptoCurrencyList

                    cryptoAdapter.updateData(list)
                    binding.spinKitView.visibility = View.GONE
                }
            }
        }
        searchCoin()
        return binding.root

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setUpRvForMarketFragment()
//    }

//    private fun setUpRvForMarketFragment(){
//        cryptoAdapter = MarketAdapter("market")
//
//        binding.currencyRecyclerView.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
//            adapter = cryptoAdapter
//        }
//        viewModel.cryptoResponse.observe(requireActivity()){response->
//            cryptoAdapter.marketCrypto = response.data.cryptoCurrencyList
//
//            binding.spinKitView.visibility = View.GONE
//        }
//    }

    private fun searchCoin() {
        binding.searchEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                searchText = p0.toString().lowercase(Locale.getDefault())

                updateRecyclerView()
            }

        })
    }


    private fun updateRecyclerView() {
        val data = ArrayList<CryptoCurrency>()

        for (item in list){
            val coinName = item.name.lowercase(Locale.getDefault())
            val coinSymbol = item.symbol.lowercase(Locale.getDefault())

            if (coinName.contains(searchText) || coinSymbol.contains(searchText)){
                data.add(item)
            }

        }
        cryptoAdapter.updateData(data)
    }

}