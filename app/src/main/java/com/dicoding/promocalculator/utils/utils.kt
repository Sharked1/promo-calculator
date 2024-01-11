package com.dicoding.promocalculator.utils

import com.dicoding.promocalculator.ui.screen.percentageInput
import java.text.DecimalFormat

fun validateNumber(value: String, inputType: String): String {
    var newValue = value
    val pointCount = newValue.count {
        it == '.'
    }
    if (pointCount > 1) {
        newValue = newValue.dropLast(1)
    }
    if (newValue.endsWith(',') || newValue.endsWith(' ') || newValue.endsWith('-')){
        newValue = newValue.dropLast(1)
    }
    if (newValue.isEmpty()) {
        return ""
    }
    if (newValue == ".") {
        return "."
    }
    if (inputType == percentageInput) {
        if (newValue.toDouble() > 100) {
            newValue = "100"
        }
    }
    return newValue
}
fun isValidInput(vararg values: String): Boolean {
    for (value in values){
        if (value.isEmpty()) {
            return false
        }
        if (value == ".") {
            return false
        }
    }
    return true
}
fun calculateDiscount(percentage: String, price: String): String {
    val value = (percentage.toDouble() * price.toDouble() / 100L).toString()
    return formatDecimal(value)
}
fun calculateFinalPrice(price: String, discount: String): String {
    val value = (price.toDouble() - discount.toDouble()).toString()
    return formatDecimal(value)
}

fun calculatePriceNeeded(percentage: String, maxPromoValue: String) : String {
    val value = (maxPromoValue.toDouble() * 100 / percentage.toDouble()).toString()
    return formatDecimal(value)
}

fun calculateDiscountValue(realPrice: String, promoPrice: String) : String {
    val value = ((-100 * promoPrice.toDouble()) / realPrice.toDouble() + 100).toString()
    return formatDecimal(value)
}

fun calculateTax(price: String) : String {
    val value = (price.toDouble() / 10).toString()
    return formatDecimal(value)
}

fun calculatePriceAfterTax(price: String, tax: String): String {
    val value = (price.toDouble() + tax.toDouble()).toString()
    return formatDecimal(value)
}
fun formatDecimal(value: String) : String {
    return DecimalFormat("#.##").format(value.toDouble())
}