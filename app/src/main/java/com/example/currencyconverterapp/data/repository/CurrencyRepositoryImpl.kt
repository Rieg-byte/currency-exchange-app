package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.remote.CurrencyRemoteDataSourceImpl
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val currencyRemoteDataSource: CurrencyRemoteDataSourceImpl) : CurrencyRepository {
    override suspend fun getCurrencyList(): List<Currency> {
        return currencyRemoteDataSource.getCurrencyList().currency.values.toList()
    }
}