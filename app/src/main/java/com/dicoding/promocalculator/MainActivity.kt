package com.dicoding.promocalculator

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.outlined.Discount
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Percent
import androidx.compose.material.icons.outlined.PriceCheck
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dicoding.promocalculator.ui.navigation.NavigationItem
import com.dicoding.promocalculator.ui.navigation.Screen
import com.dicoding.promocalculator.ui.screen.maxpromo.MaxPromoScreen
import com.dicoding.promocalculator.ui.screen.promocalculator.PromoCalculatorScreen
import com.dicoding.promocalculator.ui.screen.promopercentage.PromoPercentageScreen
import com.dicoding.promocalculator.ui.screen.taxcalculator.TaxCalculatorScreen
import com.dicoding.promocalculator.ui.theme.PromoCalculatorTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PromoCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val navController = rememberNavController()
                    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 4 } )
                    val coroutineScope = rememberCoroutineScope()
                    Scaffold(
                        topBar = {
                            MyTopBar(
                                title = getTopBarTitle(pagerState.currentPage, context),
                                navController = navController,
                            )
                        },
                        bottomBar = {
                            BottomBar(
                                navController = navController,
                                currentPage = pagerState.currentPage ,
                                pageNumber = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(it)
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->
//                        NavHost(
//                            navController = navController,
//                            startDestination = Screen.PromoCalculator.route,
//                            modifier = Modifier.padding(innerPadding),
//
//                        ) {
//                            composable(Screen.PromoCalculator.route){
//                                PromoCalculatorScreen()
//                            }
//                            composable(Screen.MaxPromo.route) {
//                                MaxPromoScreen()
//                            }
//                            composable(Screen.promoPercentage.route) {
//                                PromoPercentageScreen()
//                            }
//                            composable(Screen.TaxCalculator.route) {
//                                TaxCalculatorScreen()
//                            }
//                        }
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            HorizontalPager(
                                state = pagerState,
                                verticalAlignment = Alignment.Top,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {page ->
                                when(page){
                                    0 -> PromoCalculatorScreen()
                                    1 -> MaxPromoScreen()
                                    2 -> PromoPercentageScreen()
                                    3 -> TaxCalculatorScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String,
    navController: NavHostController
) {
    TopAppBar(
        title = { Text(text = title) },
    )
}

@Composable
fun BottomBar(
    navController: NavHostController,
    pageNumber: (Int) -> Unit,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.discount),
            selectedIcon = Icons.Filled.Discount,
            unSelectedIcon = Icons.Outlined.Discount,
            pageIndex = 0,
            screen = Screen.PromoCalculator
        ),
        NavigationItem(
            title = stringResource(id = R.string.max_promo),
            selectedIcon = Icons.Filled.PriceCheck,
            unSelectedIcon = Icons.Outlined.PriceCheck,
            pageIndex = 1,
            screen = Screen.MaxPromo
        ),
        NavigationItem(
            title = stringResource(R.string.diskon_percentage_symbol),
            selectedIcon = Icons.Filled.Percent,
            unSelectedIcon = Icons.Outlined.Percent,
            pageIndex = 2,
            screen = Screen.promoPercentage
        ),
        NavigationItem(
            title = stringResource(R.string.tax_10_percent),
            selectedIcon = Icons.Filled.MonetizationOn,
            unSelectedIcon = Icons.Outlined.MonetizationOn,
            pageIndex = 3,
            screen = Screen.TaxCalculator
        )
    )


    NavigationBar {
//        val navBackstackEntry = navController.currentBackStackEntryAsState()
//        val currentRoute = navBackstackEntry.value?.destination?.route
        navigationItems.map {
            NavigationBarItem(
                selected = it.pageIndex == currentPage,
                onClick = {
//                    navController.navigate(it.screen.route) {
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        restoreState = true
//                        launchSingleTop = true
//                    }
                          pageNumber(it.pageIndex)
                },
                icon = {
                    Icon(
                        imageVector = if (navController.currentDestination?.route == it.screen.route) it.selectedIcon else it.unSelectedIcon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = it.title)
                }
            )
        }
    }
}

fun getTopBarTitle(pageIndex: Int, context: Context): String {
    return when(pageIndex) {
        0 -> context.getString(R.string.app_name)
        1 -> context.getString(R.string.max_promo)
        2 -> context.getString(R.string.discount_percentage)
        else -> context.getString(R.string.tax)
    }
}