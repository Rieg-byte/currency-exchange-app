package com.rieg.currencyconverterapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rieg.currencyconverterapp.ui.screens.currencyConverter.CurrencyConverterScreen
import com.rieg.currencyconverterapp.ui.screens.currencyConverter.CurrencyConverterViewModel
import com.rieg.currencyconverterapp.ui.screens.home.HomeScreen
import com.rieg.currencyconverterapp.ui.screens.home.CurrencyListViewModel

@Composable
fun CurrencyConverterNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = CurrencyConverterDestination.CURRENCY_LIST.name
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(CurrencyConverterDestination.CURRENCY_LIST.name) {
            val currencyListViewModel = hiltViewModel<CurrencyListViewModel>()
            HomeScreen(
                currencyListViewModel = currencyListViewModel,
                onNavigateToConverter = { charCode ->
                navController.navigate(
                    "${CurrencyConverterDestination.CURRENCY_CONVERTER.name}/$charCode"
                ) {
                    launchSingleTop = true
                }
            })
        }
        composable(
            route = "${CurrencyConverterDestination.CURRENCY_CONVERTER.name}/{charCode}",
            arguments = listOf(navArgument("charCode") {type = NavType.StringType})
        ) {
            val currencyConverterViewModel = hiltViewModel<CurrencyConverterViewModel>()
            CurrencyConverterScreen(currencyConverterViewModel = currencyConverterViewModel)
        }
    }
}