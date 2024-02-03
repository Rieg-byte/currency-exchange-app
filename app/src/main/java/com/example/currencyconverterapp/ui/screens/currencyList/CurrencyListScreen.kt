package com.example.currencyconverterapp.ui.screens.currencyList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverterapp.data.model.Currency

@Composable
fun CurrencyListScreen(currencyListViewModel: CurrencyListViewModel = viewModel()) {
    val homeUiState by currencyListViewModel.homeUiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn() {
            items(homeUiState.listOfCurrency) {
                CurrencyCard(it)
            }
        }
    }
}



@Composable
fun CurrencyCard(currency: Currency){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = "${currency.nominal} ${currency.charCode}"
                )
                Text(
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = currency.name
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = "%.2f".format(currency.value)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    text = "(%.2f)".format(currency.value - currency.previous)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyCardPreview(){
    CurrencyCard(
        Currency(
            id = "2",
            numCode = "011",
            charCode = "USD",
            nominal = 1,
            name = "Американский доллар",
            value = 90.11,
            previous = 92.01
        )
    )
}