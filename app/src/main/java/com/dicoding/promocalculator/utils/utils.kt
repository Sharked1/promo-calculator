package com.dicoding.promocalculator.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat


val numberFormat: NumberFormat = NumberFormat.getInstance()
val decimalSeparator = DecimalFormatSymbols.getInstance().decimalSeparator

fun validatePercentage(value: String): String {
    var newValue = value
    newValue = newValue.removeLastNonDigitExceptPeriod()
    val pointCount = newValue.count {
        it == '.'
    }
    if (pointCount > 1) {
        return newValue.dropLast(1) // remove last point mark if there is 2 point mark in value
    }
    if (newValue.isEmpty()) {
        return ""
    }
    if (newValue == ".") {
        return ""
    }
    if (newValue.toDouble() > 100) {
        return "100"
    }
    return newValue
}

fun String.removeLastNonDigit(): String {
    return if (lastOrNull()?.isDigit() != false) {
        this
    } else {
        dropLast(1)
    }
}

fun String.removeLastNonDigitExceptPeriod() : String {
    return if (lastOrNull()?.isDigit() != false || last() == '.') {
        this
    } else {
        dropLast(1)
    }
}

fun formatNumber(input: String): String {
    val sanitizedInput = input.removeLastNonDigit()

    return try {
        val value = numberFormat.parse(sanitizedInput)
        numberFormat.format(value)
    } catch (e: Exception) {
        sanitizedInput
    }
}

fun changeDecimalSeparatorToComma(input: Float): String {
    return if (decimalSeparator == ',') {
        input.toString().replace('.', ',')
    } else {
        input.toString()
    }
}

fun isZeroValue(vararg values: Float) : Boolean {
    for (value in values) {
        if (value == 0f) {
            return true
        }
    }
    return false
}

fun formatDecimal(value: Float): Float {
    return DecimalFormat("#.##").format(value).toFloat()
}