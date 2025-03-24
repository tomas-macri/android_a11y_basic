package com.tomasmacri.accessibilitybasicviews.ui.detail_coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomasmacri.accessibilitybasicviews.domain.model.Coin
import com.tomasmacri.accessibilitybasicviews.repository.CoinRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class DetailCoinViewModel : ViewModel() {

    private val _coin: MutableStateFlow<Coin?> = MutableStateFlow(null)
    val coin: StateFlow<Coin?> = _coin.asStateFlow()


    fun loadCoin(coinCode: String) {
        _coin.update {
            CoinRepository.findCoinByCode(coinCode)
        }
    }

    fun changePrice() {
        viewModelScope.launch {
            delay(3000)
            _coin.update {
                it?.copy(price = Random.nextInt(1, 1000) * 1000)
            }
        }
    }

    fun changeDescription() {
        viewModelScope.launch {
            delay(3000)
            _coin.update {
                it?.copy(description = "Esta es la nueva descripción de la moneda")
            }
        }
    }


}