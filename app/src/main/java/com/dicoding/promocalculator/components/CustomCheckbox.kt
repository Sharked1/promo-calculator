package com.dicoding.promocalculator.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dicoding.promocalculator.R

@Composable
fun MyCheckBox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isChecked) MaterialTheme.colorScheme.primary else Color.White,
        animationSpec = tween(durationMillis = 200),
        label = ""
    )
    val translation by animateFloatAsState(
        targetValue = if (isChecked) 6f else 0f, animationSpec = tween(200),
        label = ""
    )
    Row(
        modifier = Modifier
            .pointerInput(isChecked) {
                detectTapGestures {
                    onCheckedChange(!isChecked)
                }
            }
            .offset(translation.dp, 0.dp)
    ) {
        Box(
            modifier = modifier
                .size(24.dp)
                .background(
                    color = backgroundColor,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = if (isChecked) CheckboxDefaults.colors().checkedBorderColor else CheckboxDefaults.colors().uncheckedBorderColor,
                    shape = CircleShape
                )
                .padding(1.dp)

        ) {
            if (isChecked) {
                Image(painter = painterResource(id = R.drawable.baseline_check_24), contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        label()
    }
}