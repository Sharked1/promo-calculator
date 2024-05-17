package com.dicoding.promocalculator.ui.screen.maxpromo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.promocalculator.R
import com.dicoding.promocalculator.components.PercentageTextField
import com.dicoding.promocalculator.components.PriceTextField
import com.dicoding.promocalculator.utils.changeDecimalSeparatorToComma
import com.dicoding.promocalculator.utils.formatNumber
import com.dicoding.promocalculator.utils.isZeroValue

@Composable
fun MaxPromoScreen(
    modifier: Modifier = Modifier,
    viewModel: MaxPromoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val maxPromoTextFieldState by viewModel.maxPromoTextFieldState.collectAsState()
    val percentageTextFieldState by viewModel.percentageTextFieldValue.collectAsState()
    val maxPromoValue by viewModel.maxPromoState.collectAsState()
    val percentage by viewModel.percentageState.collectAsState()
    val priceNeeded by viewModel.priceNeededState.collectAsState()


    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.max_promo_caption),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        PriceTextField(
            label = stringResource(id = R.string.max_promo),
            state = maxPromoTextFieldState,
            onValueChange = {
                viewModel.onMaxPromoValueChange(it)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PercentageTextField(
            state = percentageTextFieldState,
            onValueChange = {
                viewModel.onPercentageValueChange(it)
            },
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (!isZeroValue(maxPromoValue, percentage)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.purchase),
                    color = Color(0,155,0),
                    fontSize = 20.sp
                )
                Text(
                    text = formatNumber(changeDecimalSeparatorToComma(priceNeeded)),
                    color = Color(0,155,0),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

        }
    }
}