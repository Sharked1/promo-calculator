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
import com.dicoding.promocalculator.utils.calculatePriceNeeded
import com.dicoding.promocalculator.utils.isValidInput
import com.dicoding.promocalculator.utils.validateNumber
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PromoPercentageScreen(
    modifier: Modifier = Modifier
) {
    val numberFormat = NumberFormat.getInstance(Locale.ITALIAN)
    var realPrice by remember { mutableStateOf("") }
    var promoPrice by remember { mutableStateOf("") }
    var discountValue by remember { mutableStateOf("") }
    val a = "Berapa Persen Promo Tersebut?"

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
            value = realPrice,
            onValueChange = {
                realPrice = validateNumber(it, priceInput)
                if (isValidInput(realPrice, promoPrice)) {
                    discountValue = calculateDiscountValue(realPrice, promoPrice)
                }
            },
            label = { Text(text = "Harga Normal") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                if (realPrice.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            realPrice = ""
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = promoPrice,
            onValueChange = {
                promoPrice = validateNumber(it, priceInput)
                if (isValidInput(realPrice, promoPrice)) {
                    discountValue = calculateDiscountValue(realPrice, promoPrice)
                }
            },
            label = { Text(text = "Harga Promo") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                if (promoPrice.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            promoPrice = ""
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (discountValue.isNotEmpty()) {
            Text(
                text = "Diskon: ${numberFormat.format(discountValue.toDouble())}%",
                color = Color(0,155,0),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
    }
}