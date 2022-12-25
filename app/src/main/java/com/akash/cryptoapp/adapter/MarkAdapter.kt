package com.akash.cryptoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.akash.cryptoapp.R
import com.akash.cryptoapp.databinding.CurrencyItemLayoutBinding
import com.akash.cryptoapp.fragments.HomeFragmentDirections
import com.akash.cryptoapp.fragments.MarketFragmentDirections
import com.akash.cryptoapp.model.CryptoCurrency

class MarkAdapter(var context: Context, var list: List<CryptoCurrency>, var type: String) :
    RecyclerView.Adapter<MarkAdapter.MarketViewHolder>() {

    inner class MarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = CurrencyItemLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        return MarketViewHolder(
            LayoutInflater.from(context).inflate(R.layout.currency_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val item = list[position]

        //cryptoName&symbol
        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol

        //cryptoImg
        holder.binding.currencyImageView.load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png"
        ) {
            placeholder(R.drawable.spinner)

        }

        //cryptoChart
        holder.binding.currencyChartImageView.load(
            "https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + item.id + ".png"
        ) {
            placeholder(R.drawable.spinner)
            crossfade(true)
        }

        //changeIn24Hr&caretUp&down
        if (item.quotes!![0].percentChange24h > 0) {
            holder.binding.currencyPriceTextView.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.currencyChangeImageView.setImageResource(R.drawable.ic_caret_up)
            holder.binding.currencyPriceTextView.text =
                "+${String.format("%.02f", item.quotes[0].percentChange24h)}%"
        } else {
            holder.binding.currencyPriceTextView.setTextColor(context.resources.getColor(R.color.red))
            holder.binding.currencyChangeImageView.setImageResource(R.drawable.ic_caret_down)
            holder.binding.currencyPriceTextView.text =
                "${String.format("%.02f", item.quotes[0].percentChange24h)}%"
        }

        holder.itemView.setOnClickListener {
            if (type == "home") {

                findNavController(it).navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(item)
                )
            } else if (type == "market"){
                findNavController(it).navigate(
                    MarketFragmentDirections.actionMarketFragmentToDetailFragment(item)
                )
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}