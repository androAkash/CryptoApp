package com.akash.cryptoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akash.cryptoapp.R
import com.akash.cryptoapp.adapter.FavouriteCryptoAdapter
import com.akash.cryptoapp.databinding.FragmentWatchListBinding
import com.akash.cryptoapp.model.FavouriteCryptoCurrency
import com.akash.cryptoapp.viewModel.CryptoViewModel
import com.google.android.material.snackbar.Snackbar

class WatchListFragment : Fragment() {

    private lateinit var binding : FragmentWatchListBinding
    private lateinit var viewModel : CryptoViewModel
    private lateinit var cryptoAdapter : FavouriteCryptoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchListBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CryptoViewModel::class.java)



        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val cryptos = cryptoAdapter.differ.currentList[position]

                viewModel.deleteCrypto(cryptos)

                Snackbar.make(view,"Crypto has been removed",Snackbar.LENGTH_SHORT).show()


            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }

        setupRv()
    }

    private fun setupRv() {
        cryptoAdapter = FavouriteCryptoAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(
                object : DividerItemDecoration(
                    activity,LinearLayoutManager.VERTICAL
                ){})
            adapter = cryptoAdapter
        }

        viewModel.getAllFavCryptos().observe(viewLifecycleOwner){cryptoResponse->
            cryptoAdapter.differ.submitList(cryptoResponse)
            updateUi(cryptoResponse)
        }
    }

    private fun updateUi(cryptoResponse: List<FavouriteCryptoCurrency>) {
        if (cryptoResponse.isNotEmpty()){
            binding.cardNoAvailable.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }else{
            binding.recyclerView.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }
    }
}