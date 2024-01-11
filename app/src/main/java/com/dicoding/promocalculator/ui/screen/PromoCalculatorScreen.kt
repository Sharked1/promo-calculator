package com.dicoding.promocalculator.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.promocalculator.utils.calculateDiscount
import com.dicoding.promocalculator.utils.calculateFinalPrice
import com.dicoding.promocalculator.utils.isValidInput
import com.dicoding.promocalculator.utils.validateNumber
import java.text.NumberFormat
import java.util.Locale

const val percentageInput = "percentage_input"
const val priceInput = "price_input"

@Composable
fun PromoCalculatorScreen() {
    val numberFormat = NumberFormat.getInstance(Locale.ITALIAN)
    var percentage by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf("") }
    var finalPrice by remember { mutableStateOf("") }
    val a = "Hitung Harga Setelah Diskon"

    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .padding(16.dp)
        ) {
            Text(
                text = a,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
        OutlinedTextField(
            value = price,
            onValueChange = {
                price = validateNumber(it, priceInput)
                if (isValidInput(percentage, price)){
                    discount = calculateDiscount(percentage, price)
                    finalPrice = calculateFinalPrice(price, discount)
                }
            },
            label = { Text(text = "Harga") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                if (price.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            price = ""
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = percentage,
            onValueChange = {
                percentage = validateNumber(it, percentageInput)
                if (isValidInput(percentage, price)){
                    discount = calculateDiscount(percentage, price)
                    finalPrice = calculateFinalPrice(price, discount)
                }
            },
            label = { Text(text = "%") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                if (percentage.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            percentage = ""
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(0.3F)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (finalPrice.isNotEmpty()) {
            Text(
                text = "Harga: ${numberFormat.format(finalPrice.toDouble())}",
                color = Color(0,155,0),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Hemat: ${numberFormat.format(discount.toDouble())}",
                color = Color(155,0,0),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
    }
}

private fun formatNumber(input: String): String {
    return try {
        val numberFormat = NumberFormat.getInstance(Locale.US)
        val value = numberFormat.parse(input.replace(",", ""))
        numberFormat.format(value)
    } catch (e: Exception) {
        input
    }
}


