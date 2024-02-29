package com.dicoding.promocalculator.ui.screen.promocalculator

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.dicoding.promocalculator.utils.calculateDiscount
import com.dicoding.promocalculator.utils.calculateFinalPrice
import com.dicoding.promocalculator.utils.numberFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PromoCalculatorViewModel: ViewModel() {
    private val _priceTFState = MutableStateFlow(TextFieldValue(""))
    val priceTFState: StateFlow<TextFieldValue> = _priceTFState

    private val _percentageTFState = MutableStateFlow("")
    val percentageTFState: StateFlow<String> = _percentageTFState

    private val _priceState = MutableStateFlow(0f)
    val priceState: StateFlow<Float> = _priceState

    private val _percentageState = MutableStateFlow(0f)
    val percentageState: StateFlow<Float> = _percentageState

    private val _discountState = MutableStateFlow(0f)
    val discountState: StateFlow<Float> = _discountState

    private val _finalPriceState = MutableStateFlow(0f)
    val finalPriceState = _finalPriceState.asStateFlow()

    fun onpriceValueChange(value: TextFieldValue) {
        _priceTFState.value = value.copy(value.text, value.selection)
        if (value.text.isNotEmpty()) {
            _priceState.value = numberFormat.parse(value.text)?.toFloat() ?: 0f
            _discountState.value = calculateDiscount(percentageState.value, priceState.value)
            _finalPriceState.value = calculateFinalPrice(priceState.value, discountState.value)
        } else {
            _priceState.value = 0f
        }
    }

    fun onPercentageValueChange(value: String) {
        _percentageTFState.value = value
        if (value.isNotEmpty()) {
            _percentageState.value = value.toFloat()
            _discountState.value = calculateDiscount(percentageState.value, priceState.value)
            _finalPriceState.value = calculateFinalPrice(priceState.value, discountState.value)
        } else {
            _percentageState.value = 0f
        }
    }
}