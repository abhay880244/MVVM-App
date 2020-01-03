package com.abhay.mvvmapp.ui.home.Quotes

import androidx.lifecycle.ViewModel
import com.abhay.mvvmapp.data.repositories.QuotesRepository
import com.abhay.mvvmapp.util.lazyDeferred

class QuotesViewModel(
    repository: QuotesRepository
) : ViewModel() {
    val quotes by lazyDeferred {
        repository.getQuotes()
    }

}

