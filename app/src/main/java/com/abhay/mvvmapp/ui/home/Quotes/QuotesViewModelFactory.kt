package com.abhay.mvvmapp.ui.home.Quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhay.mvvmapp.data.repositories.QuotesRepository

@Suppress("UNCHECKED_CAST")
class QuotesViewModelFactory(private val repository: QuotesRepository) :
    ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuotesViewModel(repository) as T
    }
}