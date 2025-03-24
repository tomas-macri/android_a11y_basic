package com.tomasmacri.accessibilitybasiccompose.ui.routes

import kotlinx.serialization.Serializable


@Serializable
sealed class Screen {
    companion object {
        fun fromRoute(route: String?): Screen? {
            if (route == null) {
                return null
            }
            return Screen::class.sealedSubclasses.firstOrNull {
                route.contains(it.qualifiedName.toString())
            }?.objectInstance
        }
    }

    @Serializable
    data object Coins: Screen()

    @Serializable
    data class CoinDetails(val coinCode: String): Screen()

    @Serializable
    data object AddCoin: Screen()

    @Serializable
    data object WebView: Screen()

}