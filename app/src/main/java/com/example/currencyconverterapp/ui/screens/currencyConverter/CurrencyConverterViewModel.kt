package com.example.currencyconverterapp.ui.screens.currencyConverter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val currencyRepository: CurrencyRepository
): ViewModel() {
    private val charCode: String = checkNotNull(savedStateHandle["charCode"])
    private val _currencyConverterUiState =
        MutableStateFlow<CurrencyConverterUiState>(CurrencyConverterUiState.Loading)
    val currencyConverterUiState = _currencyConverterUiState.asStateFlow()
    private var amountCurrency = 0.0
    private var nominal = 0

    init {
        getCurrency(charCode)
    }

    fun repeat() {
        getCurrency(charCode)
    }

    fun updateValue(value: String) {
        val amount = value.toDoubleOrNull()
        if (_currencyConverterUiState.value !is CurrencyConverterUiState.Success) return
        if (amount != null) {
            _currencyConverterUiState.value = CurrencyConverterUiState.Success(
                fromCurrency = charCode,
                fromValue = value,
                toValue = "%.2f".format(convert(amount)),
                wrongValue = false
            )
        } else if(value.isBlank()) {
            _currencyConverterUiState.value = CurrencyConverterUiState.Success(
                fromCurrency = charCode,
                fromValue = "0",
                toValue = "0.0",
                wrongValue = false
            )
        }else {
            _currencyConverterUiState.value = CurrencyConverterUiState.Success(
                fromCurrency = charCode,
                fromValue = value,
                wrongValue = true
            )
        }
    }

    private fun getCurrency(charCode: String) = viewModelScope.launch {
        try {
            loading()
            val result = currencyRepository.getCurrency(charCode)
            if (result != null) {
                amountCurrency = result.value
                nominal = result.nominal
                _currencyConverterUiState.value = CurrencyConverterUiState.Success(
                    fromCurrency = charCode,
                    fromValue = nominal.toString(),
                    toValue = "%.2f".format(convert(nominal))
                )
            } else {
                error()
            }
        } catch (e: HttpException) {
            error()
        } catch (e: IOException) {
            error()
        }
    }

    private fun error() {
        _currencyConverterUiState.value = CurrencyConverterUiState.Error
    }

    private fun loading() {
        _currencyConverterUiState.value = CurrencyConverterUiState.Loading
    }

    private fun convert(value: Double): Double {
        return (value * amountCurrency) / nominal
    }

    private fun convert(value: Int): Double {
        return (value * amountCurrency) / nominal
    }
}