package com.tomasmacri.accessibilitybasiccompose.ui.screens.add_coin

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
class AddCoinViewModel @Inject constructor(private val repository: CoinRepository) : ViewModel() {

    private val _wasCoinAdded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val wasCoinAdded: StateFlow<Boolean> = _wasCoinAdded.asStateFlow()

    fun addCoin(coin: Coin) {
        _wasCoinAdded.update { repository.addCoin(coin) }
    }

}