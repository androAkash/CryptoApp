package com.akash.cryptoapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.akash.cryptoapp.R
import com.akash.cryptoapp.databinding.CurrencyItemLayoutBinding
import com.akash.cryptoapp.fragments.HomeFragmentDirections
import com.akash.cryptoapp.fragments.MarketFragmentDirections
import com.akash.cryptoapp.model.CryptoCurrency

class MarketAdapter(var type: String) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    inner class MarketViewHolder(val binding: CurrencyItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<CryptoCurrency>() {
        override fun areItemsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)
    var marketCrypto: List<CryptoCurrency>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    fun updateData(dataItem: List<CryptoCurrency>){
        marketCrypto = dataItem
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        return MarketViewHolder(
            CurrencyItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val item = marketCrypto[position]
        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol

        //for showing which crypto
        holder.binding.currencyImageView.load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png"
        ){
            placeholder(R.drawable.spinner)
        }

        //for chart
        holder.binding.currencyChartImageView.load(
            "https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + item.id + ".png"
        ){
            placeholder(R.drawable.spinner)
            crossfade(true)
        }

        //changeIn24Hr&caretUp&down
        if (item.quotes!![0].percentChange24h > 0) {
            holder.binding.currencyPriceTextView.setTextColor(Color.GREEN)
            holder.binding.currencyChangeImageView.setImageResource(R.drawable.ic_caret_up)
            holder.binding.currencyPriceTextView.text =
                "+${String.format("%.02f", item.quotes[0].percentChange24h)}%"
        } else {
            holder.binding.currencyPriceTextView.setTextColor(Color.RED)
            holder.binding.currencyChangeImageView.setImageResource(R.drawable.ic_caret_down)
            holder.binding.currencyPriceTextView.text =
                "${String.format("%.02f", item.quotes[0].percentChange24h)}%"
        }
        holder.itemView.setOnClickListener {

            if (type == "home") {

                Navigation.findNavController(it).navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(item)
                )
            } else if (type == "market"){
                Navigation.findNavController(it).navigate(
                    MarketFragmentDirections.actionMarketFragmentToDetailFragment(item)
                )
            }
        }
    }

    override fun getItemCount() = marketCrypto.size
}