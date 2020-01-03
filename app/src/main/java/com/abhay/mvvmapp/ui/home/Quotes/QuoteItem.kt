package com.abhay.mvvmapp.ui.home.Quotes

import com.abhay.mvvmapp.R
import com.abhay.mvvmapp.data.db.entities.Quote
import com.abhay.mvvmapp.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(private val quote: Quote) : BindableItem<ItemQuoteBinding>() {
    override fun getLayout() = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {

        viewBinding.setQuote(quote)
    }
}