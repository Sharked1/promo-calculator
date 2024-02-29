package com.dicoding.promocalculator.utils

fun calculateDiscount(percentage: Float, price: Float): Float {
    return formatDecimal(percentage * price / 100L)
}
fun calculateFinalPrice(price: Float, discount: Float): Float {
    return formatDecimal(price - discount)
}

fun calculatePriceNeeded(percentage: Float, maxPromoValue: Float) : Float {
    return formatDecimal(maxPromoValue * 100 / percentage)
}

fun calculateDiscountValue(realPrice: Float, promoPrice: Float) : Float {
    return formatDecimal((-100 * promoPrice) / realPrice + 100)
}

fun calculateTax(price: Float) : Float {
    return formatDecimal(price / 10)
}

fun calculatePriceAfterTax(price: Float, tax: Float): Float {
    return formatDecimal(price + tax)
}