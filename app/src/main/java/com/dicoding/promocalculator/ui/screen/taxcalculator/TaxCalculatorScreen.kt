package com.dicoding.promocalculator.ui.screen.taxcalculator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.promocalculator.R
import com.dicoding.promocalculator.components.MyCheckBox
import com.dicoding.promocalculator.components.PercentageTextField
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
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {

        val priceTextFieldState by viewModel.priceTFState.collectAsState()
        val priceBeforeTax by viewModel.priceBeforeTaxState.collectAsState()
        val priceAfterTax by viewModel.priceAfterTaxState.collectAsState()
        val taxValue by viewModel.taxState.collectAsState()
        val totalPriceBeforeTax by viewModel.totalPriceBeforeTaxState.collectAsState()
        val totalPriceAfterTax by viewModel.totalPriceAfterTaxState.collectAsState()
        val totalTax by viewModel.totalTaxState.collectAsState()
        val isHaveService by viewModel.isHaveServiceState.collectAsState()
        val serviceTFState by viewModel.serviceTFState.collectAsState()
        val serviceChargeValue by viewModel.serviceChargeValue.collectAsState()
        val totalServiceCharge by viewModel.totalServiceCharge.collectAsState()
        val localContext = LocalContext.current
        val clipboardManager = localContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val keyboardController = LocalSoftwareKeyboardController.current

        LaunchedEffect(isHaveService) {
            keyboardController?.hide()
        }

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



        Spacer(modifier = Modifier.height(8.dp))

        MyCheckBox(
            isChecked = isHaveService,
            onCheckedChange = {
                viewModel.onServiceCheckboxChange(it)
            },
            label = {
                Text(text = "Have service charge?")
            },
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(8.dp))

        PriceTextField(
            label = stringResource(id = R.string.price_tv),
            state = priceTextFieldState,
            imeAction = ImeAction.Done,
            onValueChange = {
                viewModel.onPriceTFValueChange(it)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

//        if (isHaveService) {
        AnimatedVisibility(
            visible = isHaveService,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300))
        ) {
            PercentageTextField(
                state = serviceTFState,
                onValueChange = {
                    viewModel.onServiceTFValueChange(it)
                },
                supportingText = {
                    Text(
                        text = stringResource(R.string.service_charge_percentage),
                        fontSize = 10.sp,
                        fontStyle = FontStyle.Italic
                    )
                },
            )
        }

//        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (!isZeroValue(priceBeforeTax)){
                if (isHaveService) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.service_charge_with_colon),
                            color = Color(155,0,0),
                            fontSize = 20.sp
                        )

                        Text(
                            text = formatNumber(changeDecimalSeparatorToComma(serviceChargeValue)),
                            color = Color(155,0,0),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }

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
                Spacer(modifier = modifier.height(12.dp))

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

                    Spacer(modifier = Modifier.width(12.dp))

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
                    .padding(4.dp)
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

                if (isHaveService) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.service_charge_with_colon),
                            fontSize = 16.sp
                        )
                        Text(
                            text = formatNumber(changeDecimalSeparatorToComma(totalServiceCharge)),
                            fontSize = 16.sp
                        )
                    }
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
                    .background(Color.Black)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.total_with_colon),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                val clip = ClipData.newPlainText("Total", formatNumber(changeDecimalSeparatorToComma(totalPriceAfterTax)))
                                clipboardManager.setPrimaryClip(clip)
                                Toast.makeText(localContext, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.CopyAll, contentDescription = "")
                        }

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
}
