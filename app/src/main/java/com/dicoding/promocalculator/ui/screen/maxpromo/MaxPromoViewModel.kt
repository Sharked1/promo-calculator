package com.dicoding.promocalculator.ui.screen.maxpromo

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.dicoding.promocalculator.utils.calculatePriceNeeded
import com.dicoding.promocalculator.utils.numberFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MaxPromoViewModel: ViewModel() {

    private val _maxPromoTextFieldState = MutableStateFlow(TextFieldValue(text = ""))
    val maxPromoTextFieldState: StateFlow<TextFieldValue> = _maxPromoTextFieldState

    private val _percentageTextFieldState = MutableStateFlow("")
    val percentageTextFieldValue: StateFlow<String> = _percentageTextFieldState

    private val _maxPromoState = MutableStateFlow(0f)
    val maxPromoState: StateFlow<Float> = _maxPromoState

    private val _percentageState = MutableStateFlow(0f)
    val percentageState: StateFlow<Float> = _percentageState

    private val _priceNeededState = MutableStateFlow(0f)
    val priceNeededState: StateFlow<Float> = _priceNeededState

    fun onMaxPromoValueChange(value: TextFieldValue) {
        _maxPromoTextFieldState.value = value.copy(value.text, value.selection)
        if (value.text.isNotEmpty()) {
            _maxPromoState.value = numberFormat.parse(value.text)?.toFloat() ?: 0f
        } else {
            _maxPromoState.value = 0f
        }
        if (percentageState.value != 0f) {
            _priceNeededState.value = calculatePriceNeeded(percentageState.value, maxPromoState.value)
        }
    }

    fun onPercentageValueChange(value: String) {
        _percentageTextFieldState.value = value
        if (value.isNotEmpty()) {
            _percentageState.value = value.toFloat()
            _priceNeededState.value = calculatePriceNeeded(percentageState.value, maxPromoState.value)
        } else {
            _percentageState.value = 0f
        }
    }

}