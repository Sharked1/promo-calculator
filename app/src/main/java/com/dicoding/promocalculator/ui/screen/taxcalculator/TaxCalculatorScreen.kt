package com.dicoding.promocalculator.ui.screen.taxcalculator

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
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
fun TaxCalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: TaxCalculatorViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Column(
        modifier = modifier
            .padding(12.dp)
    ) {
        val priceTextFieldState by viewModel.priceTFState.collectAsState()
        val priceBeforeTax by viewModel.priceBeforeTaxState.collectAsState()
        val priceAfterTax by viewModel.priceAfterTaxState.collectAsState()
        val taxValue by viewModel.taxState.collectAsState()
        val totalPriceBeforeTax by viewModel.totalPriceBeforeTaxState.collectAsState()
        val totalPriceAfterTax by viewModel.totalPriceAfterTaxState.collectAsState()
        val totalTax by viewModel.totalTaxState.collectAsState()
        
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
                text = stringResource(id = R.string.tax_calculator_caption),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        PriceTextField(
            label = stringResource(id = R.string.price_tv),
            state = priceTextFieldState,
            imeAction = ImeAction.Done,
            onValueChange = {
                viewModel.onPriceTFValueChange(it)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (!isZeroValue(priceBeforeTax)){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.tax_with_colon),
                        color = Color(155,0,0),
                        fontSize = 20.sp
                    )
                    Text(
                        text = formatNumber(changeDecimalSeparatorToComma(taxValue)),
                        color = Color(155,0,0),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.total_with_colon),
                        color = Color(0,155,0),
                        fontSize = 20.sp
                    )

                    Text(
                        text = formatNumber(changeDecimalSeparatorToComma(priceAfterTax)),
                        color = Color(0,155,0),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = modifier.height(32.dp))

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = {
                            viewModel.add()
                        },
                        modifier = modifier.weight(1f)
                    ) {
                        Text(text = stringResource(R.string.add))
                    }
                    FilledTonalButton(
                        onClick = {
                            viewModel.clear()
                        },
                        modifier = modifier.weight(1f)
                    ) {
                        Text(text = stringResource(R.string.delete))
                    }
                }
            }
        }


        Spacer(modifier = modifier.height(6.dp))

        if (!isZeroValue(totalTax, totalPriceAfterTax)) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        Color(200, 200, 200),
                        RoundedCornerShape(3.dp)
                    )
                    .padding(2.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.price),
                        fontSize = 16.sp
                    )
                    Text(
                        text = formatNumber(changeDecimalSeparatorToComma(totalPriceBeforeTax)),
                        fontSize = 16.sp
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.tax_with_colon),
                        fontSize = 16.sp
                    )
                    Text(
                        text = formatNumber(changeDecimalSeparatorToComma(totalTax)),
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(Color(0, 0, 0))
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.total_with_colon),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = formatNumber(changeDecimalSeparatorToComma(totalPriceAfterTax)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

            }
        }
    }
}
