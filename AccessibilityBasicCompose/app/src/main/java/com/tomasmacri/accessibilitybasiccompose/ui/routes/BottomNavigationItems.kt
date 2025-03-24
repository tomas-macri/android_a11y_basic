package com.tomasmacri.accessibilitybasiccompose.ui.routes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tomasmacri.accessibilitybasiccompose.R
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavigationItems<T: Screen>(val route: T, @DrawableRes val icon: Int, @StringRes val label: Int) {
    @Serializable data object NavItemCoins: BottomNavigationItems<Screen.Coins>(Screen.Coins, R.drawable.baseline_currency_bitcoin_24, R.string.tab_coin_title)
    @Serializable data object NavItemAddCoin: BottomNavigationItems<Screen.AddCoin>(Screen.AddCoin, R.drawable.baseline_add_24, R.string.tab_add_title)
    @Serializable data object NavItemWebView: BottomNavigationItems<Screen.WebView>(Screen.WebView, R.drawable.baseline_web_24, R.string.tab_webview_title)
}