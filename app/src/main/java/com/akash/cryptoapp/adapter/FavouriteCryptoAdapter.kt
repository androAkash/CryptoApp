package com.akash.cryptoapp.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.akash.cryptoapp.R
import com.akash.cryptoapp.databinding.SaveCurrencyLayoutBinding
import com.akash.cryptoapp.model.FavouriteCryptoCurrency

class FavouriteCryptoAdapter() : RecyclerView.Adapter<FavouriteCryptoAdapter.FavHeroViewHolder>() {

    inner class FavHeroViewHolder(val binding: SaveCurrencyLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<FavouriteCryptoCurrency>() {
        override fun areItemsTheSame(
            oldItem: FavouriteCryptoCurrency,
            newItem: FavouriteCryptoCurrency
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavouriteCryptoCurrency,
            newItem: FavouriteCryptoCurrency
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    var crypto: List<FavouriteCryptoCurrency>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHeroViewHolder {
        return FavHeroViewHolder(
            SaveCurrencyLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavHeroViewHolder, position: Int) {
        val currentCrypto = crypto[position]


        holder.binding.currencyNameTextView.text = currentCrypto.name
        holder.binding.currencySymbolTextView.text = currentCrypto.symbol

        holder.binding.currencyImageView.load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + currentCrypto.id + ".png"
        )

        //cryptoChart
        holder.binding.currencyChartImageView.load(
            "https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + currentCrypto.id + ".png"
        ) {
            placeholder(R.drawable.spinner)
            crossfade(true)
        }

        //changeIn24Hr&caretUp&down
        if (currentCrypto.quotes!![0].percentChange24h > 0) {
            holder.binding.currencyPriceTextView.setTextColor(Color.GREEN)
            holder.binding.currencyChangeImageView.setImageResource(R.drawable.ic_caret_up)
            holder.binding.currencyPriceTextView.text =
                "+${String.format("%.02f", currentCrypto.quotes[0].percentChange24h)}%"
        } else {
            holder.binding.currencyPriceTextView.setTextColor(Color.RED)
            holder.binding.currencyChangeImageView.setImageResource(R.drawable.ic_caret_down)
            holder.binding.currencyPriceTextView.text =
                "${String.format("%.02f", currentCrypto.quotes[0].percentChange24h)}%"
        }

        holder.itemView.setOnClickListener {

        }


    }

    override fun getItemCount() = crypto.size
}
