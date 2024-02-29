package com.dicoding.promocalculator.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.dicoding.promocalculator.utils.formatNumber

@Composable
fun PriceTextField(
    label: String,
    state: TextFieldValue,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (TextFieldValue) -> Unit,
) {
    OutlinedTextField(
        value = state,
        onValueChange = {
            val value = formatNumber(it.text)
            val formattedText = TextFieldValue(text = value, selection = TextRange(value.length))
            onValueChange(formattedText)
        },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = imeAction
        ),
        trailingIcon = {
            if (state.text.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onValueChange(TextFieldValue("", selection = TextRange(0)))
                    }
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
    )
}