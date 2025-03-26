package com.tomasmacri.accessibilitybasiccompose.ui.screens.detail_coin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.tomasmacri.accessibilitybasiccompose.R
import com.tomasmacri.accessibilitybasiccompose.domain.model.Coin
import com.tomasmacri.accessibilitybasiccompose.ui.BasePreview
import com.tomasmacri.accessibilitybasiccompose.ui.theme.Purple40

@Composable
fun CoinDetailsScreenRoot(
    viewModel: DetailCoinViewModel,
    coinCode: String,
    onCreateCoinClicked: () -> Unit
) {
    val coin by viewModel.coin.collectAsStateWithLifecycle()
    CoinDetails(
        coinCode,
        coin,
        onLoadCoin = { viewModel.loadCoin(it) },
        onChangeCoinDescriptionClicked = { viewModel.changeDescription() },
        onChangeCoinPriceClicked = { viewModel.changePrice() },
        onCreateCoinClicked = { onCreateCoinClicked() }
    )
}

@Composable
fun CoinDetails(
    coinCode: String,
    coin: Coin? = null,
    onLoadCoin: (String) -> Unit,
    onChangeCoinDescriptionClicked: () -> Unit,
    onChangeCoinPriceClicked: () -> Unit,
    onCreateCoinClicked: () -> Unit
) {

    LaunchedEffect(Unit) {
        onLoadCoin(coinCode)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(bottom = 24.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .semantics(mergeDescendants = true) {
                    heading()

                    //¡¡¡ Issue - After being read once, liveRegion will only be read as long as
                    //the content description of the @Composable is changed. !!!
                    //contentDescription = "As it is a @Composable with merged descendants I think it's impossible to add a contentDescription that:"
                        //1. Doesn't alter the order that Talkback uses to read the element
                        //2. Doesn't repeat the same information
                    //POSSIBLE SOLUTION (not ideal):
                    //val localView = LocalView.current /* Must be called in the context of a @Composable function */
                    //localView.announceForAccessibility("My announcement")

                    //¡¡¡ Issue 2 - liveRegion in Compose will only announce the new value if the @Composable is visible when the change is done !!!
                    liveRegion = LiveRegionMode.Assertive


                },
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 25.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = coin?.imageUrl,
                    placeholder = painterResource(R.drawable.bitcoin_logo),
                    contentDescription = stringResource(id = R.string.coin_logo, coin?.name ?: stringResource(R.string.coin_logo_content_description_no_coin)),
                    modifier = Modifier
                        .size(100.dp)
                        .fillMaxWidth(0.2f)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = "${coin?.name} (${coin?.code})",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 12.dp),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = "${coin?.price}€",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.semantics {
                        contentDescription = "${coin?.price}€"
                    }
                )
            }
        }

        CoinDetailsHeader(stringResource(R.string.description))

        Text(
            text = coin?.description ?: stringResource(R.string.lorem_ipsum),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .semantics {
                    //¡¡¡ Issue - After being read once, liveRegion will only be read as long as
                    //the content description of the @Composable is changed. !!!
                    liveRegion = LiveRegionMode.Polite
                    contentDescription = coin?.description ?: ""
                },
            fontSize = 16.sp
        )

        CoinDetailsHeader(stringResource(R.string.operate))

        CoinDetailsOperateButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.small_change)
        ) {
            onChangeCoinDescriptionClicked()
        }

        CoinDetailsOperateButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.big_change)
        ) {
            onChangeCoinPriceClicked()
        }

        CoinDetailsOperateButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.create_coin)
        ) {
            onCreateCoinClicked()
        }
    }
}

@Composable
fun CoinDetailsOperateButton(modifier: Modifier, text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .wrapContentWidth()
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Purple40)
    ) {
        Text(text = text)
    }
}

@Composable
private fun CoinDetailsHeader(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 12.dp)
            .semantics {
                heading()
            }
    )
}

@PreviewScreenSizes
@PreviewFontScale
@Composable
fun PreviewCoinDetails() {
    BasePreview {
        CoinDetails(
            coinCode = "BTC", coin = Coin(
                "Bitcoin",
                "BTC",
                "Bitcoin es la primera criptomoneda descentralizada y la más conocida a nivel mundial. Fue creado por una persona o grupo bajo el pseudónimo de Satoshi Nakamoto en 2008. Su objetivo es permitir pagos de igual a igual sin la necesidad de una autoridad central.",
                46000,
                "https://cryptologos.cc/logos/bitcoin-btc-logo.png",
                isFavorite = true
            ), onLoadCoin = {}, onChangeCoinDescriptionClicked = {}, onChangeCoinPriceClicked = {}, onCreateCoinClicked = {}
        )
    }
}