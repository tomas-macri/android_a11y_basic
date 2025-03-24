package com.tomasmacri.accessibilitybasicviews.ui.add_coin

import androidx.lifecycle.ViewModel
import com.tomasmacri.accessibilitybasicviews.domain.model.Coin
import com.tomasmacri.accessibilitybasicviews.repository.CoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddCoinViewModel : ViewModel() {

    private val _wasCoinAdded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val wasCoinAdded: StateFlow<Boolean> = _wasCoinAdded.asStateFlow()

    fun addCoin(coin: Coin) {
        _wasCoinAdded.update { CoinRepository.addCoin(coin) }
    }

}