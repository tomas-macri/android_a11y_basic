package com.tomasmacri.accessibilitybasiccompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tomasmacri.accessibilitybasiccompose.R
import com.tomasmacri.accessibilitybasiccompose.ui.routes.BottomNavigationItems
import com.tomasmacri.accessibilitybasiccompose.ui.routes.Screen
import com.tomasmacri.accessibilitybasiccompose.ui.screens.add_coin.AddCoinScreenRoot
import com.tomasmacri.accessibilitybasiccompose.ui.screens.add_coin.AddCoinViewModel
import com.tomasmacri.accessibilitybasiccompose.ui.screens.coins_list.CoinsScreenRoot
import com.tomasmacri.accessibilitybasiccompose.ui.screens.coins_list.CoinsViewModel
import com.tomasmacri.accessibilitybasiccompose.ui.screens.detail_coin.CoinDetailsScreenRoot
import com.tomasmacri.accessibilitybasiccompose.ui.screens.detail_coin.DetailCoinViewModel
import com.tomasmacri.accessibilitybasiccompose.ui.screens.webview.WebViewScreenRoot
import com.tomasmacri.accessibilitybasiccompose.ui.theme.AccessibilityBasicComposeTheme
import com.tomasmacri.accessibilitybasiccompose.ui.theme.Orange
import com.tomasmacri.accessibilitybasiccompose.ui.theme.Purple40
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AccessibilityBasicComposeTheme {
                val navController = rememberNavController()
                var topAppBarTitle by remember { mutableStateOf("") }
                BaseScaffold(navController, topAppBarTitle) {
                    NavHost(navController = navController, startDestination = Screen.Coins) {
                        composable<Screen.Coins> {
                            topAppBarTitle = stringResource(R.string.coins_title)
                            CoinsScreenRoot(
                                viewModel = hiltViewModel<CoinsViewModel>(),
                                onCoinClicked = { coin ->
                                    navController.navigate(Screen.CoinDetails(coin.code))
                                })

                        }
                        composable<Screen.AddCoin> {
                            topAppBarTitle = stringResource(R.string.add_coin_title)
                            AddCoinScreenRoot(
                                viewModel = hiltViewModel<AddCoinViewModel>(),
                                onCoinAdded = {
                                    navController.navigateUp()
                                }
                            )
                        }
                        composable<Screen.CoinDetails> {
                            val args = it.toRoute<Screen.CoinDetails>()
                            topAppBarTitle = stringResource(R.string.coin_details_title)
                            CoinDetailsScreenRoot(
                                viewModel = hiltViewModel<DetailCoinViewModel>(),
                                coinCode = args.coinCode
                            ) {
                                navController.navigate(Screen.AddCoin)
                            }
                        }

                        composable<Screen.WebView> {
                            topAppBarTitle = stringResource(R.string.tab_webview_title)
                            WebViewScreenRoot()
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun BaseScaffold(navController: NavHostController, topAppBarTitle: String, content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            AccessibilityBasicComposeTopAppBar(navController, topAppBarTitle)
        },
        bottomBar = {
            AccessibilityBasicComposeNavigationBar(navController)
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccessibilityBasicComposeTopAppBar(navController: NavHostController, topAppBarTitle: String) {
    TopAppBar(
        title = {
            Text(topAppBarTitle)
        },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton (
                    onClick = { navController.navigateUp() }
                ) {
                    Icon (
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_icon_content_description),
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = Purple40,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

@Composable
private fun AccessibilityBasicComposeNavigationBar(navController: NavHostController) {
    val items = listOf(BottomNavigationItems.NavItemCoins, BottomNavigationItems.NavItemAddCoin, BottomNavigationItems.NavItemWebView)
    NavigationBar(
        containerColor = Color.Black,
        contentColor = Orange
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = Screen.fromRoute(navBackStackEntry?.destination?.route)
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute?.javaClass == item.route.javaClass,
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = stringResource(item.label),
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.label),
                        fontSize = 12.sp,
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Orange,
                    selectedTextColor = Orange,
                    selectedIndicatorColor = Color.Transparent,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    disabledIconColor = Color.White,
                    disabledTextColor = Color.White
                ),
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { ruta ->
                            popUpTo(ruta) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun BasePreview(content: @Composable () -> Unit) {
    val fakeNavController = rememberNavController()
    val title = "PREVIEW"
    BaseScaffold(fakeNavController, title) {
        content()
    }

}