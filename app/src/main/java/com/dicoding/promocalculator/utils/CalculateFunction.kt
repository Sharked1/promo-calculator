package com.dicoding.promocalculator.utils

import com.dicoding.promocalculator.utils.DecimalUtils.formatDecimal

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

fun calculateService(price: Float, servicePercentage: Float): Float {
    return price * servicePercentage / 100
}
fun calculateTax(price: Float, serviceCharge: Float) : Float {
    return formatDecimal((price + serviceCharge) / 10)
}

fun calculatePriceAfterTax(price: Float, serviceCharge: Float, tax: Float): Float {
    return formatDecimal(price + serviceCharge + tax)
}