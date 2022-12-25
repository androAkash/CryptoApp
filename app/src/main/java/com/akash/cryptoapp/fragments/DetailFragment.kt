package com.akash.cryptoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.akash.cryptoapp.R
import com.akash.cryptoapp.animation.ButtonBounceInterpolator
import com.akash.cryptoapp.databinding.FragmentDetailBinding
import com.akash.cryptoapp.model.CryptoCurrency
import com.akash.cryptoapp.model.FavouriteCryptoCurrency
import com.akash.cryptoapp.viewModel.CryptoViewModel
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel : CryptoViewModel
    private val args : DetailFragmentArgs by navArgs()
    private lateinit var selectedCrypto : CryptoCurrency

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)

        selectedCrypto = args.marketModel!!

        setUpDetailScreen()
        setButtonOnClick(selectedCrypto)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CryptoViewModel::class.java)

        binding.addWatchlistButton.setOnClickListener {

            val myAnim : Animation = AnimationUtils.loadAnimation(requireContext(),R.anim.bounce)

            val interpolator = ButtonBounceInterpolator(0.2,12.0)
            myAnim.interpolator = interpolator

            binding.addWatchlistButton.startAnimation(myAnim)

            addFavCrypto(view)
        }

        binding.backStackButton.setOnClickListener {
            findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToHomeFragment()
            )
        }

    }

    private fun setUpDetailScreen(){
        //1 Image
        binding.detailImageView.load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + selectedCrypto.id + ".png"
        ){
            placeholder(
                R.drawable.spinner
            )
            //ImageText
            binding.detailPriceTextView.text = "${String.format("$%.02f", selectedCrypto.quotes[0].price)}%"
        }
        //TitleText
        binding.detailSymbolTextView.text = selectedCrypto.name

        //If textChanges
        if (selectedCrypto.quotes[0].percentChange24h > 0) {
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.green))
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_up)
            binding.detailChangeTextView.text =
                "+${String.format("%.02f", selectedCrypto.quotes[0].percentChange24h)}%"
        } else {
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.red))
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_down)
            binding.detailChangeTextView.text =
                "${String.format("%.02f", selectedCrypto.quotes[0].percentChange24h)}%"
        }
        //loadChart
        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + selectedCrypto.symbol
                    + "USD&interval=D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=" +
                    "F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=" +
                    "[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )

        binding.turnover.text = selectedCrypto.quotes[0].turnover.toString()
        binding.volume.text = selectedCrypto.quotes[0].volume24h.toString()
        binding.marketPairCount.text = selectedCrypto.marketPairCount.toString()

    }


    /**For Each button candle chart**/
    private fun candleChartData(
        it: View?,
        s: String,
        item: CryptoCurrency,
        oneDay: AppCompatButton,
        oneMonth: AppCompatButton,
        oneWeek: AppCompatButton,
        fourHour: AppCompatButton,
        oneHour: AppCompatButton
    ) {

        disableButton(oneDay,oneHour,oneMonth,oneWeek,fourHour)
        it!!.setBackgroundResource(R.drawable.active_button)

        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + item.symbol
                    + "USD&interval="+s+"&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=" +
                    "F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=" +
                    "[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )
    }

    //*For Disable button*//
    private fun disableButton(
        oneDay: AppCompatButton,
        oneHour: AppCompatButton,
        oneMonth: AppCompatButton,
        oneWeek: AppCompatButton,
        fourHour: AppCompatButton
    ) {
        oneDay.background = null
        oneHour.background = null
        oneMonth.background = null
        oneWeek.background = null
        fourHour.background = null
    }

    /**For SetOnClickListener in every button**/
    private fun setButtonOnClick(item: CryptoCurrency) {
        val oneMonth = binding.button
        val oneWeek = binding.button1
        val oneDay = binding.button2
        val fourHour = binding.button3
        val oneHour = binding.button4
        val fifteenMinute = binding.button5

        val clickListener = View.OnClickListener {
            when(it.id){
                fifteenMinute.id -> candleChartData(it, "15", item, oneDay,oneMonth,oneWeek,fourHour,oneHour)
                oneMonth.id -> candleChartData(it, "M", item, oneDay,fifteenMinute,oneWeek,fourHour,oneHour)
                oneWeek.id -> candleChartData(it, "1W", item, oneDay,oneMonth,fifteenMinute,fourHour,oneHour)
                oneDay.id -> candleChartData(it, "D", item, fifteenMinute,oneMonth,oneWeek,fourHour,oneHour)
                fourHour.id -> candleChartData(it, "4H", item, oneDay,oneMonth,oneWeek,fifteenMinute,oneHour)
                oneHour.id -> candleChartData(it, "H", item, oneDay,oneMonth,oneWeek,fourHour,fifteenMinute)
            }
        }

        oneMonth.setOnClickListener(clickListener)
        oneWeek.setOnClickListener(clickListener)
        oneDay.setOnClickListener(clickListener)
        fourHour.setOnClickListener(clickListener)
        oneHour.setOnClickListener(clickListener)
        fifteenMinute.setOnClickListener(clickListener)
    }

    //savingCrypto
    private fun addFavCrypto(view: View){
        val favCrypto = FavouriteCryptoCurrency(
            0,selectedCrypto.name,selectedCrypto.slug,selectedCrypto.symbol,selectedCrypto.quotes,selectedCrypto.totalSupply
        )
        viewModel.addFavCrypto(favCrypto)
        Snackbar.make(view,"cryptoSaved",Snackbar.LENGTH_SHORT).show()
    }

}