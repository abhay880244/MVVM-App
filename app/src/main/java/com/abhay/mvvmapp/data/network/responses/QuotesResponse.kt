package com.abhay.mvvmapp.data.network.responses

import com.abhay.mvvmapp.data.db.entities.Quote

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)