package com.tomasmacri.accessibilitybasiccompose.ui.screens.detail_coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomasmacri.accessibilitybasiccompose.domain.model.Coin
import com.tomasmacri.accessibilitybasiccompose.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DetailCoinViewModel @Inject constructor(private val repository: CoinRepository) : ViewModel() {

    private val _coin: MutableStateFlow<Coin?> = MutableStateFlow(null)
    val coin: StateFlow<Coin?> = _coin.asStateFlow()


    fun loadCoin(coinCode: String) {
        _coin.update {
            repository.findCoinByCode(coinCode)
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
                it?.copy(description = "Esta es la nueva descripción de la moneda".plus(Random.nextInt(until = 100)))
            }
        }
    }


}