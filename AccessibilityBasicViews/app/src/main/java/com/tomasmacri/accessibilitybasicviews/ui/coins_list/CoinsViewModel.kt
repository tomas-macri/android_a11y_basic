package com.tomasmacri.accessibilitybasicviews.ui.coins_list

import androidx.lifecycle.ViewModel
import com.tomasmacri.accessibilitybasicviews.domain.model.Coin
import com.tomasmacri.accessibilitybasicviews.repository.CoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CoinsViewModel: ViewModel() {

    private val _coinsList: MutableStateFlow<List<Coin>?> = MutableStateFlow(null)
    val coinList: StateFlow<List<Coin>?> = _coinsList.asStateFlow()

    fun getCoins() {
        _coinsList.update {
            CoinRepository.getCoins()
        }
    }

    fun deleteCoin(coin: Coin) {
        _coinsList.update {
            CoinRepository.deleteCoin(coin)
        }
    }

    fun changeFavoriteStatus(coin: Coin) {
        _coinsList.update {
            CoinRepository.updateCoin(coin.copy(isFavorite = coin.isFavorite.not()))
        }
    }
}