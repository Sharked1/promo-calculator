package com.dicoding.promocalculator.ui.screen.promopercentage

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.dicoding.promocalculator.utils.calculateDiscountValue
import com.dicoding.promocalculator.utils.numberFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PromoPercentageViewModel: ViewModel() {

    private val _realpriceState = MutableStateFlow(0f)
    val realPriceState: StateFlow<Float> = _realpriceState

    private val _promoPriceState = MutableStateFlow(0f)
    val promoPriceState: StateFlow<Float> = _promoPriceState

    private val _discountState = MutableStateFlow(0f)
    val discountState: StateFlow<Float> = _discountState

    private val _realPriceTFState = MutableStateFlow(TextFieldValue(""))
    val realPriceTFState: StateFlow<TextFieldValue> = _realPriceTFState

    private val _promoPriceTFState = MutableStateFlow(TextFieldValue(""))
    val promoPriceTFState: StateFlow<TextFieldValue> = _promoPriceTFState

    fun onRealPriceValueChange(value: TextFieldValue) {
        _realPriceTFState.value = value.copy(value.text, value.selection)
        if (value.text.isNotEmpty()) {
            _realpriceState.value = numberFormat.parse(value.text)?.toFloat() ?: 0f
            _discountState.value = calculateDiscountValue(realPriceState.value, promoPriceState.value)
        } else {
            _realpriceState.value = 0f
        }
    }

    fun onPromoPriceValueChange(value: TextFieldValue) {
        _promoPriceTFState.value = value.copy(value.text, value.selection)
        if (value.text.isNotEmpty()) {
            _promoPriceState.value = numberFormat.parse(value.text)?.toFloat() ?: 0f
        } else {
            _promoPriceState.value = 0f
        }
        if (realPriceState.value != 0f) {
            _discountState.value = calculateDiscountValue(realPriceState.value, promoPriceState.value)
        }
    }

}