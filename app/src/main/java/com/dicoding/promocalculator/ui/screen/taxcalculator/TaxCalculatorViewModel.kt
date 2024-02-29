package com.dicoding.promocalculator.ui.screen.taxcalculator

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.dicoding.promocalculator.utils.calculatePriceAfterTax
import com.dicoding.promocalculator.utils.calculateTax
import com.dicoding.promocalculator.utils.numberFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaxCalculatorViewModel: ViewModel() {

    private val _priceTFState = MutableStateFlow(TextFieldValue(""))
    val priceTFState: StateFlow<TextFieldValue> = _priceTFState

    private val _priceBeforeTaxState = MutableStateFlow(0f)
    val priceBeforeTaxState: StateFlow<Float> = _priceBeforeTaxState

    private val _priceAfterTaxState = MutableStateFlow(0f)
    val priceAfterTaxState: StateFlow<Float> = _priceAfterTaxState

    private val _taxState = MutableStateFlow(0f)
    val taxState: StateFlow<Float> = _taxState

    private val _totalPriceBeforeTaxState = MutableStateFlow(0f)
    val totalPriceBeforeTaxState: StateFlow<Float> = _totalPriceBeforeTaxState

    private val _totalPriceAfterTaxState = MutableStateFlow(0f)
    val totalPriceAfterTaxState: StateFlow<Float> = _totalPriceAfterTaxState

    private val _totalTaxState = MutableStateFlow(0f)
    val totalTaxState: StateFlow<Float> = _totalTaxState

    fun onPriceTFValueChange(value: TextFieldValue) {
        _priceTFState.value = value.copy(value.text, value.selection)
        if (value.text.isNotEmpty()) {
            _priceBeforeTaxState.value = numberFormat.parse(value.text)?.toFloat() ?: 0f
            _taxState.value = calculateTax(priceBeforeTaxState.value)
            _priceAfterTaxState.value = calculatePriceAfterTax(_priceBeforeTaxState.value, taxState.value)
        } else {
            _priceBeforeTaxState.value = 0f
        }
    }

    fun add() {
        _totalPriceBeforeTaxState.value += priceBeforeTaxState.value
        _totalPriceAfterTaxState.value += priceAfterTaxState.value
        _totalTaxState.value += taxState.value
        _priceTFState.value = TextFieldValue("")
    }

    fun clear() {
        _totalPriceBeforeTaxState.value = 0f
        _totalPriceAfterTaxState.value = 0f
        _totalTaxState.value = 0f
        _priceTFState.value = TextFieldValue("")
        _priceBeforeTaxState.value = 0f
        _priceAfterTaxState.value = 0f
        _taxState.value = 0f
    }
}