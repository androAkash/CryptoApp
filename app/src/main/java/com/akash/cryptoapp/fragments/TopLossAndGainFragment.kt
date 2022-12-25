package com.akash.cryptoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.akash.cryptoapp.adapter.MarkAdapter
import com.akash.cryptoapp.adapter.MarketAdapter
import com.akash.cryptoapp.databinding.FragmentTopLossAndGainBinding
import com.akash.cryptoapp.di.AppModule
import com.akash.cryptoapp.model.CryptoCurrency
import com.akash.cryptoapp.viewModel.CryptoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TopLossAndGainFragment() : Fragment() {
    private lateinit var binding: FragmentTopLossAndGainBinding
    private lateinit var viewModel: CryptoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopLossAndGainBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity()).get(CryptoViewModel::class.java)

        getMarketData()

        return binding.root
    }

    private fun getMarketData() {
        val position = requireArguments().getInt("position")

        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val res = AppModule.providesRetrofitInstance().getCrypto()

            if (res.body() != null) {

                withContext(Dispatchers.Main) {
                    val dataItem = res.body()!!.data.cryptoCurrencyList

                    Collections.sort(dataItem) { gain, loss ->
                        (loss.quotes[0].percentChange24h.toInt())
                            .compareTo(gain.quotes[0].percentChange24h.toInt())
                    }
                    binding.spinKitView.visibility = GONE

                    val list = ArrayList<CryptoCurrency>()

                    //Gain
                    if (position == 0) {
                        list.clear()

                        for (i in 0..9) {
                            list.add(dataItem[i])
                        }
                        binding.topGainLoseRecyclerView.adapter = MarkAdapter(
                            requireContext(),
                            list,
                            "home"
                        )
                    }

                    //Loss
                    else {
                        list.clear()

                        for (i in 0..9) {
                            list.add(dataItem[dataItem.size - 1 - i])
                        }
                        binding.topGainLoseRecyclerView.adapter = MarkAdapter(
                            requireContext(),
                            list,
                            "home"
                        )
                    }
                }
            }
        }
    }
}