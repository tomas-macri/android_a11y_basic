package com.tomasmacri.accessibilitybasicviews.ui.coins_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tomasmacri.accessibilitybasicviews.databinding.FragmentCoinsBinding
import com.tomasmacri.accessibilitybasicviews.domain.model.Coin
import kotlinx.coroutines.launch

class CoinsFragment : Fragment() {

    private val viewModel = CoinsViewModel()
    private lateinit var binding: FragmentCoinsBinding

    private val adapter by lazy {
        CoinsAdapter(requireContext(), object: CoinActions {
            override fun onDeleteCoin(coin: Coin) {
                viewModel.deleteCoin(coin)
            }

            override fun onChangeFavoriteCoin(coin: Coin) {
                viewModel.changeFavoriteStatus(coin)
            }

            override fun onCoinClicked(coin: Coin) {
                findNavController().navigate(CoinsFragmentDirections.actionCoinsToCoinDetails(coin.code))
            }

        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCoinsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCoinList()
        observeState()
        viewModel.getCoins()
    }

    private fun setUpCoinList() {
        with(binding) {
            val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    deleteCoin(adapter.currentList[position])
                }
            })
            rvCoins.adapter = adapter
            itemTouchHelper.attachToRecyclerView(rvCoins)
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.coinList.collect { coinList ->
                adapter.submitList(coinList)
            }
        }
    }

    fun deleteCoin(coin: Coin) {
        viewModel.deleteCoin(coin)
    }
}