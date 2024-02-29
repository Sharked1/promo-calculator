package com.dicoding.promocalculator.ui.screen.promopercentage

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.promocalculator.R
import com.dicoding.promocalculator.components.PriceTextField
import com.dicoding.promocalculator.utils.changeDecimalSeparatorToComma
import com.dicoding.promocalculator.utils.formatNumber
import com.dicoding.promocalculator.utils.isZeroValue

@Composable
fun PromoPercentageScreen(
    modifier: Modifier = Modifier,
    viewModel: PromoPercentageViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val realPriceTextFieldState by viewModel.realPriceTFState.collectAsState()
    val promoTextFieldState by viewModel.promoPriceTFState.collectAsState()
    val realPrice by viewModel.realPriceState.collectAsState()
    val promoPrice by viewModel.promoPriceState.collectAsState()
    val discountValue by viewModel.discountState.collectAsState()
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
                text = stringResource(id = R.string.promo_percentage_caption),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        PriceTextField(
            label = stringResource(R.string.normal_price),
            state = realPriceTextFieldState,
            onValueChange = {
                viewModel.onRealPriceValueChange(it)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PriceTextField(
            label = stringResource(R.string.promotion_price),
            state = promoTextFieldState,
            imeAction = ImeAction.Done,
            onValueChange = {
                viewModel.onPromoPriceValueChange(it)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (!isZeroValue(realPrice, promoPrice)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.discount_with_colon),
                    color = Color(0,155,0),
                    fontSize = 20.sp
                )
                Text(
                    text = "${formatNumber(changeDecimalSeparatorToComma(discountValue))}%",
                    color = Color(0,155,0),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

        }
    }
}