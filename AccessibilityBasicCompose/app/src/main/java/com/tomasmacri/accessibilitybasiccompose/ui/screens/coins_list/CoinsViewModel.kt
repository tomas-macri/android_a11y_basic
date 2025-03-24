package com.tomasmacri.accessibilitybasiccompose.ui.screens.coins_list

import androidx.lifecycle.ViewModel
import com.tomasmacri.accessibilitybasiccompose.domain.model.Coin
import com.tomasmacri.accessibilitybasiccompose.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(private val repository: CoinRepository): ViewModel() {

    private val _coinsList: MutableStateFlow<List<Coin>> = MutableStateFlow(emptyList())
    val coinList: StateFlow<List<Coin>> = _coinsList.asStateFlow()

    fun getCoins() {
        _coinsList.update {
            repository.getCoins()
        }
    }

    fun deleteCoin(coin: Coin) {
        _coinsList.update {
            repository.deleteCoin(coin)
        }
    }

    fun changeFavoriteStatus(coin: Coin) {
        _coinsList.update {
            repository.updateCoin(coin.copy(isFavorite = coin.isFavorite.not()))
        }
    }
}