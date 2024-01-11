package com.dicoding.promocalculator.ui.navigation

sealed class Screen(val route: String) {
    object PromoCalculator: Screen("promo_calculator")
    object MaxPromo: Screen("max_promotion")
    object promoPercentage: Screen("promo_percentage")
    object TaxCalculator: Screen("tax_calculator")
}