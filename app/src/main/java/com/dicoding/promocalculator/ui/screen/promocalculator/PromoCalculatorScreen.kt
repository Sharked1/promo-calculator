package com.dicoding.promocalculator.ui.screen.promocalculator

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
fun PromoCalculatorScreen(
    viewModel: PromoCalculatorViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val price by viewModel.priceState.collectAsState()
    val percentage by viewModel.percentageState.collectAsState()
    val discount by viewModel.discountState.collectAsState()
    val finalPrice by viewModel.finalPriceState.collectAsState()

    val priceTextFieldState by viewModel.priceTFState.collectAsState()
    val percentageTFState by viewModel.percentageTFState.collectAsState()

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
                text = stringResource(id = R.string.promo_calculator_caption),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        PriceTextField(
            label = stringResource(R.string.price_tv),
            state = priceTextFieldState,
            onValueChange = {
                viewModel.onpriceValueChange(it)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PercentageTextField(
            state = percentageTFState,
            onValueChange = {
                viewModel.onPercentageValueChange(it)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (!isZeroValue(price, percentage)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.price),
                    color = Color(0,155,0),
                    fontSize = 20.sp,
                )
                Text(
                    text = formatNumber(changeDecimalSeparatorToComma(finalPrice)),
                    color = Color(0,155,0),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.saved),
                    color = Color(155,0,0),
                    fontSize = 20.sp
                )
                Text(
                    text = formatNumber(changeDecimalSeparatorToComma(discount)),
                    color = Color(155,0,0),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}






