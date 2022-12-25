package com.akash.cryptoapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.akash.cryptoapp.databinding.TopCurrencyLayoutBinding
import com.akash.cryptoapp.model.CryptoCurrency

class CryptoAdapter : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    inner class CryptoViewHolder(val binding: TopCurrencyLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<CryptoCurrency>() {
        override fun areItemsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)
    var crypto: List<CryptoCurrency>
    get() = differ.currentList
    set(value) {
        differ.submitList(value)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        return CryptoViewHolder(TopCurrencyLayoutBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val currentCrypto = crypto[position]

        holder.binding.topCurrencyNameTextView.text = currentCrypto.name

        holder.binding.topCurrencyImageView.load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + currentCrypto.id + ".png"
        )


        //If that coin earns profit then show green; else show re
        if (currentCrypto.quotes!![0].percentChange24h > 0){
            holder.binding.topCurrencyChangeTextView.setTextColor(Color.GREEN)
            holder.binding.topCurrencyChangeTextView.text = "+${String.format("%.02f",currentCrypto.quotes[0].percentChange24h)}%"
        }
        else{
            holder.binding.topCurrencyChangeTextView.setTextColor(Color.RED)
            holder.binding.topCurrencyChangeTextView.text = "${String.format("%.02f",currentCrypto.quotes[0].percentChange24h)}%"
        }
    }

    override fun getItemCount() = crypto.size
}