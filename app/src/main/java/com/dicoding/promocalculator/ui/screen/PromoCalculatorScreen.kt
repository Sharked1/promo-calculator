package com.dicoding.promocalculator.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoCalculatorScreen() {
    var discountValue by remember { mutableStateOf("") }
    var priceValue by remember { mutableStateOf("") }
    var resultValue by remember { mutableStateOf("") }
    Scaffold(
        topBar = { MyTopBar() }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

            TextField(
                value = discountValue,
                onValueChange = { discountValue = it },
                placeholder = { Text(text = "Persentase diskon") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = priceValue,
                onValueChange = { priceValue = it },
                placeholder = { Text(text = "Harga") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                resultValue = (priceValue.toDouble() - discountValue.toDouble() * priceValue.toDouble() / 100).toString()
                priceValue = ""
                discountValue = ""
            },
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()) {
                Text(text = "Hitung")
            }
            Text(text = "Harganya jadi $resultValue")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    TopAppBar(
        title = { Text(text = "Promo Calculator") },
        actions = {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "",

            )
        }
    )
}

@Composable
fun ResultText(
    discount: Double,
    price: Double
) {
    Text(text = "Harganya jadi: ${discount * price / 100} rupiah")
}