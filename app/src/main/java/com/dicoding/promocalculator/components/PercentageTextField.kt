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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dicoding.promocalculator.utils.validatePercentage

@Composable
fun PercentageTextField(
    state: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = state,
        onValueChange = {
            onValueChange(validatePercentage(it))
        },
        label = { Text(text = "%") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            if (state.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onValueChange("")
                    }
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(0.3F)
    )
}