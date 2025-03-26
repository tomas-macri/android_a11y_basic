package com.tomasmacri.accessibilitybasiccompose.ui.screens.coins_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.tomasmacri.accessibilitybasiccompose.R
import com.tomasmacri.accessibilitybasiccompose.domain.model.Coin
import com.tomasmacri.accessibilitybasiccompose.ui.BasePreview

@Composable
fun CoinsScreenRoot(
    viewModel: CoinsViewModel,
    onCoinClicked: (Coin) -> Unit
) {
    val coinsList by viewModel.coinList.collectAsStateWithLifecycle()
    CoinsScreen(
        coinList = coinsList,
        onCoinClicked = { onCoinClicked(it) },
        onCoinSwiped = { viewModel.deleteCoin(it) },
        onCoinFavoriteIconClicked = { viewModel.changeFavoriteStatus(it) },
        loadCoins = { viewModel.getCoins() }
    )
}

@Composable
fun CoinsScreen(
    coinList: List<Coin> = emptyList(),
    onCoinClicked: (Coin) -> Unit,
    onCoinSwiped: (Coin) -> Unit,
    onCoinFavoriteIconClicked: (Coin) -> Unit,
    loadCoins: () -> Unit
) {
    LaunchedEffect(Unit) {
        loadCoins()
    }
    LazyColumn(modifier = Modifier.padding(vertical = 16.dp)) {
        items(items = coinList, key = { coin -> coin.code }) { coin ->
            Box(modifier = Modifier.animateItem()) {
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { boxValue ->
                        if (boxValue == SwipeToDismissBoxValue.EndToStart) {
                            onCoinSwiped(coin)
                            return@rememberSwipeToDismissBoxState true
                        }
                        return@rememberSwipeToDismissBoxState false
                    },
                )
                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {},
                    enableDismissFromStartToEnd = false,
                ) {
                    CoinItem(coin, onCoinClicked, onCoinSwiped, onCoinFavoriteIconClicked)
                }
            }
        }
    }
}

@Composable
fun CoinItem(
    coin: Coin,
    onCoinClicked: (Coin) -> Unit,
    onCoinSwiped: (Coin) -> Unit,
    onCoinFavoriteIconClicked: (Coin) -> Unit,
) {
    val changeFavoriteStatusActionLabel = if (coin.isFavorite) {
        stringResource(R.string.change_favorite_negative_action_label)
    } else {
        stringResource(R.string.change_favorite_positive_action_label)
    }
    val deleteCoinActionLabel = stringResource(R.string.delete_coin)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .semantics {
                customActions = listOf(
                    CustomAccessibilityAction(changeFavoriteStatusActionLabel) {
                        onCoinFavoriteIconClicked(coin)
                        true
                    },
                    CustomAccessibilityAction(deleteCoinActionLabel) {
                        onCoinSwiped(coin)
                        true
                    }
                )
            }
            .clickable { onCoinClicked(coin) },
        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = coin.imageUrl,
                contentDescription = stringResource(id = R.string.coin_logo, coin.name),
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = coin.name,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = coin.code,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                )
                Text(
                    text = coin.description,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Text(
                text = "${coin.price}€",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )

            val stateFavorite = stringResource(R.string.favorito)
            val stateNotFavorite = stringResource(R.string.no_favorito)
            IconButton(
                onClick = { onCoinFavoriteIconClicked(coin) },
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
                    .clearAndSetSemantics {
                        contentDescription = if (coin.isFavorite) stateFavorite else stateNotFavorite
                    },
            ) {
                Icon(
                    imageVector = if (coin.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = Color(0xFF8B2721),
                )
            }
        }
    }
}


//@PreviewFontScale
//@PreviewScreenSizes
//@PreviewDynamicColors
@Preview(showBackground = true, device = Devices.PIXEL_7_PRO, showSystemUi = true)
@Composable
fun CoinsScreenPreview() {
    BasePreview {
        CoinsScreen(
            coinList = mutableListOf(
                Coin(
                    "Bitcoin",
                    "BTC",
                    "Bitcoin es la primera criptomoneda descentralizada y la más conocida a nivel mundial. Fue creado por una persona o grupo bajo el pseudónimo de Satoshi Nakamoto en 2008. Su objetivo es permitir pagos de igual a igual sin la necesidad de una autoridad central.",
                    46000,
                    "https://cryptologos.cc/logos/bitcoin-btc-logo.png",
                    isFavorite = true
                ),
                Coin(
                    "Ethereum",
                    "ETH",
                    "Ethereum es una plataforma descentralizada de código abierto que ejecuta contratos inteligentes. A diferencia de Bitcoin, Ethereum permite la creación de aplicaciones descentralizadas (dApps) sobre su blockchain, lo que lo hace mucho más versátil.",
                    3300,
                    "https://cryptologos.cc/logos/ethereum-eth-logo.png"
                ),
                Coin(
                    "Litecoin",
                    "LTC",
                    "Litecoin es una criptomoneda que fue lanzada en 2011 como una alternativa más ligera y rápida a Bitcoin. Se basa en un código abierto y permite la verificación de transacciones con mayor rapidez.",
                    150,
                    "https://cryptologos.cc/logos/litecoin-ltc-logo.png",
                    isFavorite = true
                ),
                Coin(
                    "Bitcoin Cash",
                    "BCH",
                    "Bitcoin Cash es una criptomoneda que nació como un fork de Bitcoin en 2017. El objetivo de Bitcoin Cash es permitir transacciones más rápidas y más baratas, a diferencia de Bitcoin.",
                    500,
                    "https://cryptologos.cc/logos/bitcoin-cash-bch-logo.png",
                    isFavorite = true
                ),
                Coin(
                    "Cardano",
                    "ADA",
                    "Cardano es una plataforma de blockchain de tercera generación que se centra en la escalabilidad, la sostenibilidad y la interoperabilidad de las criptomonedas. Su criptomoneda nativa es ADA.",
                    1,
                    "https://cryptologos.cc/logos/cardano-ada-logo.png"
                ),
                Coin(
                    "Binance Coin",
                    "BNB",
                    "Binance Coin es la criptomoneda nativa de Binance, el mayor intercambio de criptomonedas. BNB se utiliza para pagar tarifas de transacción en Binance, entre otros usos.",
                    400,
                    "https://cryptologos.cc/logos/binance-coin-bnb-logo.png",
                    isFavorite = true
                )
            ),
            onCoinClicked = {},
            onCoinSwiped = {},
            onCoinFavoriteIconClicked = {},
            loadCoins = {}
        )
    }
}

@Preview
@Composable
fun CoinItemPreview() {
    CoinItem(
        Coin(
            "Bitcoin Cash",
            "BTC",
            "Bitcoin es la primera criptomoneda descentralizada y la más conocida a nivel mundial. Fue creado por una persona o grupo bajo el pseudónimo de Satoshi Nakamoto en 2008. Su objetivo es permitir pagos de igual a igual sin la necesidad de una autoridad central.",
            46000,
            "https://cryptologos.cc/logos/bitcoin-btc-logo.png",
            isFavorite = true
        ),
        onCoinClicked = {},
        onCoinSwiped = {},
        onCoinFavoriteIconClicked = {},
    )
}

