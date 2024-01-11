package com.dicoding.promocalculator.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.promocalculator.utils.calculateDiscountValue
import com.dicoding.promocalculator.utils.calculatePriceAfterTax
import com.dicoding.promocalculator.utils.calculateTax
import com.dicoding.promocalculator.utils.isValidInput
import com.dicoding.promocalculator.utils.validateNumber
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TaxCalculatorScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(12.dp)
    ) {
        val numberFormat = NumberFormat.getInstance(Locale.ITALIAN)
        var price by remember { mutableStateOf("") }
        var tax by remember { mutableStateOf("") }
        var priceAfterTax by remember { mutableStateOf("") }
        val a = "Hitung Harga Setelah Pajak"
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
                if (isValidInput(price)) {
                    tax = calculateTax(price)
                    priceAfterTax = calculatePriceAfterTax(price, tax)
                }
            },
            label = { Text(text = "Harga") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
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

        if (price.isNotEmpty()) {
            Text(
                text = "Tax: ${numberFormat.format(tax.toDouble())}",
                color = Color(155,0,0),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Text(
                text = "Final Price: ${numberFormat.format(priceAfterTax.toDouble())}",
                color = Color(0,155,0),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
    }
}